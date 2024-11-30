package com.kancth.navybucketstorage.domain.file.service;

import com.kancth.navybucketstorage.domain.auth.service.AuthService;
import com.kancth.navybucketstorage.domain.bucket.entity.Bucket;
import com.kancth.navybucketstorage.domain.bucket.service.BucketService;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileListResponse;
import com.kancth.navybucketstorage.domain.file.dto.CreateFileRequest;
import com.kancth.navybucketstorage.domain.file.entity.File;
import com.kancth.navybucketstorage.domain.file.repository.FileRepository;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.global.exception.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        // File name 중복 확인
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
        Path path = Paths.get("/Users/mac/Desktop/navy-cloud");

        List<EntityAndFile> entityAndFiles = new ArrayList<>();
        successedFileList.forEach((file) -> {
            Stream.of(createFileRequest.files()).forEach((multipartFile) -> {
                if (multipartFile.getOriginalFilename().equals(file.getFileName())) {
                    entityAndFiles.add(new EntityAndFile(file, multipartFile));
                }
            });
        });

        entityAndFiles.forEach((file) -> {
            try {
                Path destinationFile = path.resolve(file.entity.getFileName());
                Files.copy(file.file().getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return CreateFileListResponse.of(bucket, successedFileList, failedFileList);
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

    record EntityAndFile (
            File entity,
            MultipartFile file
    ) {}
}
