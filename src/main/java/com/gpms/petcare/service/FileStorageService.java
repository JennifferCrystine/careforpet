package com.gpms.petcare.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

@Service
public class FileStorageService {

    private AmazonS3Client s3Client;

    @Value("${aws.s3.chave}")
    private String chave;

    @Value("${aws.s3.segredo}")
    private String segredo;

    @Value("${aws.s3.bucket.raiz}")
    private String raizBucket;

    private AmazonS3Client getS3Client() {
        if (this.s3Client != null)
            return this.s3Client;

        AWSCredentials credentials = new BasicAWSCredentials(chave, segredo);

        return this.s3Client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public String salvaImagem(MultipartFile imagem, String pasta, String nome) throws IOException {

        if (imagem.isEmpty())
            throw new IllegalStateException("Não foi possível realizar upload de imagem");

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(imagem.getContentType())) {
            throw new IllegalStateException("O arquivo não é uma imagem");
        }


        String semiCaminho = pasta + "/" + nome + "." + imagem.getContentType().replaceAll(".+/", "");
        this.getS3Client().putObject("dac-petcare", semiCaminho, imagem.getInputStream(), new ObjectMetadata());

        return this.raizBucket + "/" + semiCaminho;
    }

}
