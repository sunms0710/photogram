package com.seon.photogram.service;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.image.Image;
import com.seon.photogram.domain.image.ImageRepository;
import com.seon.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.path}")
    private String filePath;
    private final ImageRepository imageRepository;

    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        Path imageFilePath = Paths.get(filePath + imageFileName);

        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
    }
}