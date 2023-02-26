package com.newland.mall.storage.utils;

import com.newland.mall.storage.properties.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Tags;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: leell
 * Date: 2023/1/12 20:41:48
 */
@Component
public class MinioClientUtils implements InitializingBean {
    @Autowired
    private MinioProperties minioProperties;
    private MinioClient minioClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        minioClient = MinioClient.builder()
                .httpClient(getUnsafeOkHttpsClient())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .endpoint(minioProperties.getUrl())
                .build();
    }

    public Map<String, String> getTags(String objectName) throws Exception {
        Tags tags = minioClient.getObjectTags(GetObjectTagsArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build());
        return tags.get();
    }

    /**
     * 获取文件外链
     * @param objectName 文件名称
     * @return url
     */
    public String getObjectURL(String objectName) throws Exception {
        StringBuilder sb = new StringBuilder(minioProperties.getUrl());
        if (!minioProperties.getUrl().endsWith("/")) {
            sb.append("/");
        }
        sb.append(minioProperties.getBucketName());
        sb.append("/");
        sb.append(objectName);
        return sb.toString();
    }

    /**
     * 获取文件外链
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    public String getObjectURL(String objectName, Integer expires) throws Exception {
        Map<String, String> tags = getTags(objectName);
        GetPresignedObjectUrlArgs.Builder builder = GetPresignedObjectUrlArgs.builder().bucket(minioProperties.getBucketName()).expiry(expires).method(Method.GET).object(objectName).extraQueryParams(tags);
        return minioClient.getPresignedObjectUrl(builder.build());
    }

    /**
     * 获取文件
     * @param objectName 文件名称
     * @return 二进制流
     */

    public InputStream getObject(String objectName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build());
    }

    /**
     * 获取文件
     * @param objectName 文件名称
     * @return 二进制流
     */

    public InputStream getObject(String objectName, long offset, Long length) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).offset(offset).length(length).build());
    }

    /**
     * 删除文件
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public void removeObject(String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioProperties.getBucketName()).object(objectName).build());
    }

    public String putObject(String objectName, File file) throws Exception {
        String filename = file.getName();
        return putObject(objectName, filename, file);
    }

    public String putObject(String objectName, String filename, File file) throws Exception {
        Map<String, String> queryParams = new HashMap<>(1);
//        queryParams.put(StorageConstant.KEY_FILE_NAME, Base64Utils.encodeToString(filename));
        FileInputStream fis = new FileInputStream(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .tags(queryParams)
                .stream(fis, fis.available(), -1)
                .build();
        minioClient.putObject(args);
        return objectName;
    }

    public String putObject(String objectName, MultipartFile file) throws Exception {
        String filename = file.getName();
        return putObject(objectName, filename, file);
    }

    public String putObject(String objectName, String filename, MultipartFile file) throws Exception {
        Map<String, String> queryParams = new HashMap<>(1);
//        queryParams.put(StorageConstant.KEY_FILE_NAME, Base64Utils.encodeToString(filename));
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(objectName)
                .tags(queryParams)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return objectName;
    }

    public OkHttpClient getUnsafeOkHttpsClient() throws KeyManagementException {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };


            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            });


            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
            return builder.build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
