package com.jack.www.gsondemo;

import java.util.ArrayList;

/**
 * Created by pc on 2016/5/24.
 */
public class Student
{
    public String name;
    public int age;
    public ArrayList<String> phone = new ArrayList<String>();
    public ArrayList<Score> score = new ArrayList<Score>();

    public class Score
    {
        public String id;
        public String fenshu;

        @Override
        public String toString()
        {
            return "Score{" +
                    "id='" + id + '\'' +
                    ", fenshu='" + fenshu + '\'' +
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone=" + phone +
                ", score=" + score +
                '}';
    }
}
