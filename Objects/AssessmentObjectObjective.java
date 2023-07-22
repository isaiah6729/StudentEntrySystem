package com.zybooks.wgu;

import java.util.ArrayList;

// get objective assessments
public class AssessmentObjectObjective {

    private final static ArrayList<AssessmentObjectObjective> ObjectiveAssessments = new ArrayList<>();

    private final int ObjectivecourseID;
    private final int Objectiveassessmentsid;
    private final String Objectiveassessments;
    private final String startDate;
    private final String endDate;

    public AssessmentObjectObjective() {
        Objectiveassessmentsid = 25;
        ObjectivecourseID = 20;
        Objectiveassessments = "assessments";
        startDate   = "date";
        endDate = "date";
    }

    public AssessmentObjectObjective(int  Objectiveassessmentsid, int ObjectivecourseID, String Objectiveassessments, String startDate , String endDate) {
        this.Objectiveassessmentsid = Objectiveassessmentsid;
        this.ObjectivecourseID = ObjectivecourseID;
        this.Objectiveassessments = Objectiveassessments;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getObjectiveassessmentsid() {
        return Objectiveassessmentsid;
    }

    public int getObjectivecourseID() {
        return ObjectivecourseID;
    }

   public static void addObjectiveAssessments (AssessmentObjectObjective assessmentsObject) {ObjectiveAssessments.add(assessmentsObject);}

    public static void deleteAssessmentsO () {ObjectiveAssessments.clear();}

    public static ArrayList<AssessmentObjectObjective> getObjectiveAssessments() {
        return ObjectiveAssessments;
    }


    public String getObjectiveassessments() {
        return Objectiveassessments;
    }
}
