package com.example.fertilizer.Soilest;

import android.graphics.Bitmap;

import java.util.List;


public interface Classifier1 {

    class Recognition {

        private final String id;

        private final String title;

        private final boolean quant;

        private final Float confidence;

        public Recognition(
                final String id, final String title, final Float confidence, final boolean quant) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
            this.quant = quant;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Float getConfidence() {
            return confidence;
        }

        @Override
        public String toString() {
            String resultString = "";
            if (id != null) {
                //resultString += "[" + id + "] ";
            }

            if (title != null) {
                resultString += title.split(" ")[1] + " ";
            }

            if (confidence != null) {
              //  resultString += String.format("(%.1f%%) ", confidence * 100.0f);
            }

            return resultString.trim();
        }
    }


    List<Recognition> recognizeImage(Bitmap bitmap);

    void close();
}