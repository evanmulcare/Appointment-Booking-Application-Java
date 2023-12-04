package com.example.evan_mulcare_assignment_two.Models;

import com.example.evan_mulcare_assignment_two.R;

import java.util.HashMap;
import java.util.Map;

public class TitleOption {
    private static final String[] PEOPLE_OPTIONS = {"man", "woman", "Transvestite", "child", "elderly person", "doctor", "teacher"};
    private static final String[] ANIMAL_OPTIONS = {" a dog", " a horse", " a cat", " a T-rex"};
    private static final String[] SEE_OPTIONS = {"See", "Visit", "Meet", "Inspect"};
    private static final String[] ABOUT_OPTIONS = {"about", "regarding", "concerning", "In relation to"};
    private static final Map<String, Integer> ANIMAL_IMAGE_MAP;

    static {
        ANIMAL_IMAGE_MAP = new HashMap<>();
        ANIMAL_IMAGE_MAP.put(ANIMAL_OPTIONS[0], R.drawable.dog);
        ANIMAL_IMAGE_MAP.put(ANIMAL_OPTIONS[1], R.drawable.horse);
        ANIMAL_IMAGE_MAP.put(ANIMAL_OPTIONS[2], R.drawable.cat);
        ANIMAL_IMAGE_MAP.put(ANIMAL_OPTIONS[3], R.drawable.fox);
    }

    public static Map<String, Integer> getAnimalImageMap() {
        return ANIMAL_IMAGE_MAP;
    }

    public static String[] getPeopleOptions() {
        return PEOPLE_OPTIONS;
    }

    public static String[] getAnimalOptions() {
        return ANIMAL_OPTIONS;
    }

    public static String[] getSeeOptions() {
        return SEE_OPTIONS;
    }

    public static String[] getAboutOptions() {
        return ABOUT_OPTIONS;
    }
}
