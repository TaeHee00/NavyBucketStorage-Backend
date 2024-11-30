package com.kancth.navybucketstorage.domain.file.service;

import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.service.BucketService;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.entity.File;
import com.kancth.navybucketstorage.domain.file.exception.FileNotFoundException;
import com.kancth.navybucketstorage.domain.file.repository.FileRepository;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final AuthService authService;
    private final BucketService bucketService;
    private final FileRepository fileRepository;

    public CreateFileListResponse create(CreateFileRequest createFileRequest, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);
        Bucket bucket = bucketService.getBucket(createFileRequest.bucketId());

        // bucket owner check
        this.checkBucketOwner(bucket, user);
        // File name 중복 확인 -> 버킷에 같은 이름을 가진 file이 있을 경우 업로드X
        Map<Boolean, List<MultipartFile>> duplicateCheckFileList = this.checkDuplicateFileList(bucket, createFileRequest.files());
        List<MultipartFile> duplicateList = new ArrayList<>();
        if (duplicateCheckFileList.get(true) != null) {
            duplicateList.addAll(duplicateCheckFileList.get(true));
        }
        List<String> failedFileList = duplicateList.stream().map(MultipartFile::getOriginalFilename).toList();

        List<File> successedFileList = new ArrayList<>();
        if (duplicateCheckFileList.get(false) != null) {
            successedFileList.addAll(fileRepository.saveAll(duplicateCheckFileList.get(false).stream().map((file) -> File.create(bucket, file)).toList()));
        }

        List<EntityAndFile> entityAndFiles = translateEntityAndFile(successedFileList, createFileRequest);
        saveFile(entityAndFiles);

        return CreateFileListResponse.of(bucket, successedFileList, failedFileList);
    }

    public Resource download(String bucketName, String fileName) {
        // TODO: Access 관리
        File file = fileRepository.findByUrl(bucketName + "/" + fileName).orElseThrow(FileNotFoundException::new);

        try {
            Path fileStorageLocation = Paths.get("/Users/mac/Desktop/navy-cloud/" + file.getPath().replaceAll("\\+", " "));
            Path filePath = fileStorageLocation.resolve(fileName.replaceAll("\\+", " ")).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException();
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException();
        }
    }

    private List<EntityAndFile> translateEntityAndFile(List<File> fileList, CreateFileRequest createFileRequest) {
        List<EntityAndFile> entityAndFiles = new ArrayList<>();
        fileList.forEach((file) -> {
            Stream.of(createFileRequest.files()).forEach((multipartFile) -> {
                if (multipartFile.getOriginalFilename().equals(file.getFileName())) {
                    entityAndFiles.add(new EntityAndFile(file, multipartFile));
                }
            });
        });

        return entityAndFiles;
    }

    private void checkBucketOwner(Bucket bucket, User user) {
        if (!bucket.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    private Map<Boolean, List<MultipartFile>> checkDuplicateFileList(Bucket bucket, MultipartFile[] files) {
        return Stream.of(files)
                .collect(Collectors.groupingBy((file) -> fileRepository.existsByBucketAndFileName(bucket, file.getOriginalFilename())));
    }

    private void createFolder(String path) {
        java.io.File folder = new java.io.File(path);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("폴더가 생성되었습니다: " + path);
            } else {
                log.error("[NCP] Security Error - 권한 문제로 인하여 폴더 생성에 실패하였습니다. error: {}", path);
            }
        }
    }

    private void saveFile(List<EntityAndFile> entityAndFiles) {
        entityAndFiles.forEach((file) -> {
            try {
                final String savePath = "/Users/mac/Desktop/navy-cloud/" + file.entity.getPath();

                createFolder(savePath);
                Path path = Paths.get(savePath);
                Path destinationFile = path.resolve(file.entity.getFileName());
                Files.copy(file.file().getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private record EntityAndFile(
            File entity,
            MultipartFile file
    ) {
    }
}

