package Assessments;

import java.util.ArrayList;

public class AssessmentObjectPerformance {

    private final static ArrayList<AssessmentObjectPerformance> PerformanceAssessments = new ArrayList<>();

    private final int courseID;
    private final int Performanceassessmentsid;
    private final String assessment;
    private final String startDate;
    private final String endDate;
    private final String title;

    public AssessmentObjectPerformance() {
        Performanceassessmentsid = 0;
        courseID = 0;
        assessment = "assessments";
        startDate   = "date";
        endDate = "date";
        title = "";
    }

    public AssessmentObjectPerformance(int  Performanceassessmentsid, int courseID, String title, String assessment, String startDate , String endDate) {
        this.Performanceassessmentsid = Performanceassessmentsid;
        this.courseID = courseID;
        this.assessment = assessment;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getPerformanceassessmentsid() {
        return Performanceassessmentsid;
    }

    public int getCourseID() {
        return courseID;
    }

    public static void addPerformanceAssessments (AssessmentObjectPerformance assessmentObjectPerformance) {PerformanceAssessments.add(assessmentObjectPerformance);}

    public static void deleteAssessmentsP () {PerformanceAssessments.clear();}
    public static ArrayList<AssessmentObjectPerformance> getPerformanceAssessments() {

        return PerformanceAssessments;
    }

    public String getAssessment() {
        return assessment;
    }

}
