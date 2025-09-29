import React, { useEffect, useState } from "react";
import { getCourses, getAssessmentsByCourse } from "../../services/api";
import "./MyAssessments.css";

export default function MyAssessments() {
  const [courses, setCourses] = useState([]);
  const [assessments, setAssessments] = useState([]);

  useEffect(() => {
    getCourses().then(res => setCourses(res.data));
  }, []);

  const loadAssessments = (courseId) => {
    getAssessmentsByCourse(courseId).then(res => setAssessments(res.data));
  };

  return (
    <div className="my-assessments-container">
      <h2>My Assessments</h2>

      <select
        className="course-select"
        onChange={(e) => loadAssessments(e.target.value)}
      >
        <option value="">Select Course</option>
        {courses.map(c => (
          <option key={c.id} value={c.id}>{c.title}</option>
        ))}
      </select>

      <ul className="assessment-list">
        {assessments.map(a => (
          <li key={a.id}>
            <span>{a.title}</span>
            <a href={`/assessments/${a.id}/take`}>Take Assessment</a>
          </li>
        ))}
      </ul>
    </div>
  );
}