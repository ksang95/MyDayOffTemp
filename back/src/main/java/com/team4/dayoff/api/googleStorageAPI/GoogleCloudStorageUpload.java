package com.team4.dayoff.api.googleStorageAPI;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.springframework.web.multipart.MultipartFile;

/** A snippet for Google Cloud Storage showing how to create a blob. */
public class GoogleCloudStorageUpload {

	public static String getImageUrl(MultipartFile file, final String bucketName, final Storage storage)
			throws IOException {
		final String fileName = file.getOriginalFilename();
		// Check extension of file
		if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
			final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
			for (String s : allowedExt) {
				if (extension.equals(s)) {
					return uploadFile(file, bucketName, storage, s);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String uploadFile(MultipartFile file, final String bucketName, final Storage storage, String type)
			throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-YYYY-MM-dd-HHmmssSSS");
		String date = simpleDateFormat.format(new Date());
		String filen = file.getOriginalFilename();
		final String fileName = filen.substring(0, filen.lastIndexOf('.')) + date
				+ filen.substring(filen.lastIndexOf('.'));
				System.out.println(1);

		BufferedInputStream stream = new BufferedInputStream(file.getInputStream());
		BlobInfo blobInfo = storage
		 		.create(BlobInfo.newBuilder(bucketName, fileName).setContentType("image/" + type).build(), stream);
		return blobInfo.getMediaLink();

		//https://storage.googleapis.com/bit-jaehoon/image.jpg : 아무나 볼 수 있는 url
		// the inputstream is closed by default, so we don't need to close it here
	
	}

	public static String saveFile(MultipartFile file) {
		// [START storage_upload_file]
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String name=null;
		try {
			String url = getImageUrl(file, "bit-jaehoon", storage);
			name=url.substring(url.lastIndexOf('/')+1,url.lastIndexOf('?'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// [END storage_upload_file]
		return name;
	}
}