package com.kancth.navybucketstorage.domain.file.service;

import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.domain.bucket.BucketAccessLevel;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.repository.BucketRepository;
import com.kancth.navybucketstorage.domain.bucket.service.BucketService;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.entity.File;
import com.kancth.navybucketstorage.domain.file.exception.FileNotFoundException;
import com.kancth.navybucketstorage.domain.file.repository.FileRepository;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final AuthService authService;
    private final BucketService bucketService;
    private final FileRepository fileRepository;
    private final BucketRepository bucketRepository;

    @Transactional
    public CreateFileListResponse create(CreateFileRequest createFileRequest, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);
        Bucket bucket = bucketService.getBucket(createFileRequest.bucketId());

        // bucket owner check
        bucketService.checkBucketOwner(bucket, user);
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

    @Transactional
    public Resource download(String bucketName, String fileName) {
        // TODO: bucket Private/Public 처리
        File file = fileRepository.findByUrl(bucketName + "/" + fileName).orElseThrow(FileNotFoundException::new);

        if (BucketAccessLevel.PRIVATE.equals(file.getBucket().getAccessLevel())) {
            throw new UnauthorizedAccessException();
        }

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

    @Transactional
    public void delete(Long fileId, HttpServletRequest request) {
        User user = authService.getCurrentUser(request);
        File file = this.getFile(fileId);
        Bucket bucket = file.getBucket();

        bucketService.checkBucketOwner(bucket, user);

        fileRepository.delete(file);
    }
    private CreateFileListResponse upload(Bucket bucket, MultipartFile[] files) {
        // File name 중복 확인 -> 버킷에 같은 이름을 가진 file이 있을 경우 업로드X
        Map<Boolean, List<MultipartFile>> duplicateCheckFileList = this.checkDuplicateFileList(bucket, files);
        List<MultipartFile> duplicateList = new ArrayList<>();
        if (duplicateCheckFileList.get(true) != null) {
            duplicateList.addAll(duplicateCheckFileList.get(true));
        }
        List<String> failedFileList = duplicateList.stream().map(MultipartFile::getOriginalFilename).toList();

        List<File> successedFileList = new ArrayList<>();
        if (duplicateCheckFileList.get(false) != null) {
            successedFileList.addAll(fileRepository.saveAll(duplicateCheckFileList.get(false).stream().map((file) -> File.create(bucket, file)).toList()));
        }

        List<EntityAndFile> entityAndFiles = translateEntityAndFile(successedFileList, files);
        saveFile(entityAndFiles);

        return CreateFileListResponse.of(bucket, successedFileList, failedFileList);
    }

    private File getFile(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
    }

    private List<EntityAndFile> translateEntityAndFile(List<File> fileList, MultipartFile[] files) {
        List<EntityAndFile> entityAndFiles = new ArrayList<>();
        fileList.forEach((file) -> {
            Stream.of(files).forEach((multipartFile) -> {
                if (multipartFile.getOriginalFilename().equals(file.getFileName())) {
                    entityAndFiles.add(new EntityAndFile(file, multipartFile));
                }
            });
        });

        return entityAndFiles;
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

