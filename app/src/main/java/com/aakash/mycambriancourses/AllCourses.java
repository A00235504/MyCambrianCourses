package com.aakash.mycambriancourses;

public class AllCourses
{

    private String coursename;
    private String coursedescription;
    private String courseduration;
    private String image;


    public AllCourses() {}

    // Getter and setter method
    public String getFirstname()
    {
        return coursename;
    }
    public void setFirstname(String coursename)
    {
        this.coursename = coursename;
    }

    public String getLastname()
    {
        return coursedescription;
    }
    public void setLastname(String coursedescription)
    { this.coursedescription = coursedescription; }

    public String getAge()
    {
        return courseduration;
    }
    public void setAge(String courseduration)
    {
        this.courseduration = courseduration;
    }

    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }

}
