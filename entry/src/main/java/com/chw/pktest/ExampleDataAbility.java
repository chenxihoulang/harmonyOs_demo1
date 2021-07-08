package com.chw.pktest;

import ohos.aafwk.ability.Ability;
import ohos.global.resource.RawFileDescriptor;
import ohos.utils.net.Uri;

import java.io.*;

public class ExampleDataAbility extends Ability {
    private static final String PLACEHOLDER_RAW_FILE = "/rawfile/";
    private static final String PLACEHOLDER_LOCAL_FILE = "/local/";
    private static final String ENTRY_PATH_PREFIX = "entry/resources";

    @Override
    public RawFileDescriptor openRawFile(Uri uri, String mode) throws FileNotFoundException {
        final int splitChar = '/';
        if (uri == null) {
            throw new FileNotFoundException("Invalid Uri");
        }

        // path will be like /com.example.dataability/rawfile/example.html
        String path = uri.getEncodedPath();
        final int splitIndex = path.indexOf(splitChar, 1);
        if (splitIndex < 0) {
            throw new FileNotFoundException("Invalid Uri " + uri);
        }

        String targetPath = path.substring(splitIndex);
        if (targetPath.startsWith(PLACEHOLDER_RAW_FILE)) {
            // 根据自定义规则访问资源文件
            try {
                return getResourceManager().getRawFileEntry(ENTRY_PATH_PREFIX + targetPath).openRawFileDescriptor();
            } catch (IOException e) {
                throw new FileNotFoundException("Not found support raw file at " + uri);
            }
        } else if (targetPath.startsWith(PLACEHOLDER_LOCAL_FILE)) {
            // 根据自定义规则访问本地文件
            File file = new File(getContext().getFilesDir(), targetPath.replace(PLACEHOLDER_LOCAL_FILE, ""));
            if (!file.exists()) {
                throw new FileNotFoundException("Not found support local file at " + uri);
            }
            return getRawFileDescriptor(file, uri);
        } else {
            throw new FileNotFoundException("Not found support file at " + uri);
        }
    }

    private RawFileDescriptor getRawFileDescriptor(File file, Uri uri) throws FileNotFoundException {
        try {
            final FileDescriptor fileDescriptor = new FileInputStream(file).getFD();
            return new RawFileDescriptor() {
                @Override
                public FileDescriptor getFileDescriptor() {
                    return fileDescriptor;
                }

                @Override
                public long getFileSize() {
                    return -1;
                }

                @Override
                public long getStartPosition() {
                    return 0;
                }

                @Override
                public void close() throws IOException {
                }
            };
        } catch (IOException e) {
            throw new FileNotFoundException("Not found support local file at " + uri);
        }
    }
}