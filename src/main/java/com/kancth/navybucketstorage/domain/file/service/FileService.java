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

import java.util.List;
import java.util.Map;
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
        List<String> failedFileList = duplicateCheckFileList.get(Boolean.TRUE).stream().map(MultipartFile::getOriginalFilename).toList();
        List<File> successedFileList = fileRepository.saveAll(duplicateCheckFileList.get(Boolean.FALSE).stream().map((file) -> File.create(bucket, file)).toList());

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
}
