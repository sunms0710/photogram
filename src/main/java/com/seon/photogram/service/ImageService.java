package com.seon.photogram.service;

import com.seon.photogram.config.auth.PrincipalDetails;
import com.seon.photogram.domain.image.Image;
import com.seon.photogram.domain.image.ImageRepository;
import com.seon.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${file.path}")
    private String filePath;
    private final ImageRepository imageRepository;

    // 이미지 업로드
    @Transactional
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
    
    // 스토리 불러오기
    @Transactional(readOnly = true)
    public Page<Image> imageStory(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        //images에 좋아요 상태 담기
        images.forEach((image) -> {

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });
        return images;
    }
    
    // 인기 스토리
    @Transactional(readOnly = true)
    public List<Image> popularImage() {
        return imageRepository.mPopular();
    }
}
