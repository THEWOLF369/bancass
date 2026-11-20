package com.star.Ba.jsf.util;

import java.io.IOException;
import java.io.OutputStream;

public class ProgressOutputStream extends OutputStream {

    private OutputStream outputStream;
    private int totalBytes;
    private int uploadedBytes = 0;
    private ProgressCallback progressCallback;

    public ProgressOutputStream(OutputStream outputStream, int totalBytes, ProgressCallback progressCallback) {
        this.outputStream = outputStream;
        this.totalBytes = totalBytes;
        this.progressCallback = progressCallback;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        uploadedBytes++;
        updateProgress();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
        uploadedBytes += len;
        updateProgress();
    }

    private void updateProgress() {
        int progress = (int) ((uploadedBytes * 100.0f) / totalBytes);
        if (progressCallback != null) {
            progressCallback.updateProgress(progress);
        }
    }

    public interface ProgressCallback {
        void updateProgress(int progress);
    }
}
