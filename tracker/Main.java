package tracker;

import tracker.learningplatform.LearningPlatform;

public class Main {

    public static void main(String[] args) {

        LearningPlatform learningPlatform = LearningPlatform.getInstance();

        learningPlatform.progressTracker();

    }

}
