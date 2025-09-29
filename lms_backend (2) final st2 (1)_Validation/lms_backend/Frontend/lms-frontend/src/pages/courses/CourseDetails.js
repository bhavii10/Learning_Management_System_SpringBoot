//import React, { useEffect, useState } from "react";
//import { getCourse } from "../../services/api";
//import { useParams } from "react-router-dom";
//import "./courses.css";
//
//export default function CourseDetails() {
//  const { id } = useParams();
//  const [course, setCourse] = useState(null);
//
//  useEffect(() => {
//    getCourse(id).then(res => setCourse(res.data));
//  }, [id]);
//
//  if (!course) return <div className="page"><p>Loading...</p></div>;
//
//  return (
//    <div className="page">
//      <h2>{course.title}</h2>
//      <p className="muted">Instructor: {course.instructor}</p>
//      <p>{course.description}</p>
//      <a className="btn" href="/courses">Back to list</a>
//    </div>
//  );
//}
//=================================================================================
//import React, { useEffect, useState } from "react";
//import { getCourse, toggleLessonAPI } from "../../services/api"; // ✅ import
//import { useParams } from "react-router-dom";
//import "./courses.css";
//
//export default function CourseDetails() {
//  const { id } = useParams();
//  const [course, setCourse] = useState(null);
//  const [checkedLessons, setCheckedLessons] = useState([]);
//
//  useEffect(() => {
//    getCourse(id).then((res) => {
//      setCourse(res.data);
//
//      // ✅ If backend already returns which lessons are completed
//      const completed = res.data.lessons
//        ?.filter((l) => l.completed) // backend should send `completed: true/false`
//        .map((l) => l.id) || [];
//      setCheckedLessons(completed);
//    });
//  }, [id]);
//
//  if (!course) return <div className="page"><p>Loading...</p></div>;
//
//  const lessons = course.lessons || [];
//
//  // ✅ Toggle lesson check + persist to backend
//  const toggleLesson = async (lessonId) => {
//    const isCompleted = !checkedLessons.includes(lessonId);
//
//    try {
//      await toggleLessonAPI(lessonId, isCompleted); // call backend
//
//      // update UI immediately
//      setCheckedLessons((prev) =>
//        isCompleted ? [...prev, lessonId] : prev.filter((id) => id !== lessonId)
//      );
//    } catch (err) {
//      console.error("Failed to toggle lesson:", err.message);
//      alert("Could not update lesson status. Please try again.");
//    }
//  };
//
//  const progressPercentage = lessons.length
//    ? (checkedLessons.length / lessons.length) * 100
//    : 0;
//
//  return (
//    <div className="page">
//      <h2>{course.title}</h2>
//      <p className="muted">Instructor: {course.instructor}</p>
//      <p>{course.description}</p>
//
//      {/* Progress Bar */}
//      {lessons.length > 0 && (
//        <>
//          <div className="progress-container">
//            <div
//              className="progress-bar"
//              style={{ width: `${progressPercentage}%` }}
//            />
//          </div>
//          <p>{Math.round(progressPercentage)}% Completed</p>
//        </>
//      )}
//
//      {/* Lessons List */}
//      {lessons.length > 0 && (
//        <ul className="lessons">
//          {lessons.map((lesson) => (
//            <li key={lesson.id}>
//              <label>
//                <input
//                  type="checkbox"
//                  checked={checkedLessons.includes(lesson.id)}
//                  onChange={() => toggleLesson(lesson.id)}
//                />
//                {lesson.title}
//              </label>
//            </li>
//          ))}
//        </ul>
//      )}
//
//      <a className="btn" href="/courses">Back to list</a>
//    </div>
//  );
//}
import React, { useEffect, useState } from "react";
import { getCourse, toggleLessonAPI, generateCertificateAPI } from "../../services/api";
import { useParams } from "react-router-dom";
import "./courses.css";

export default function CourseDetails() {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [checkedLessons, setCheckedLessons] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getCourse(id).then((res) => {
      setCourse(res.data);

      // ✅ If backend returns which lessons are already completed
      const completed = res.data.lessons
        ?.filter((l) => l.completed) // assumes backend sends `completed: true/false`
        .map((l) => l.id) || [];
      setCheckedLessons(completed);
    });
  }, [id]);

  if (!course) return <div className="page"><p>Loading...</p></div>;

  const lessons = course.lessons || [];

  // ✅ Toggle lesson check + persist to backend
  const toggleLesson = async (lessonId) => {
    const isCompleted = !checkedLessons.includes(lessonId);

    try {
      await toggleLessonAPI(lessonId, isCompleted); // call backend

      // update UI immediately
      setCheckedLessons((prev) =>
        isCompleted ? [...prev, lessonId] : prev.filter((id) => id !== lessonId)
      );
    } catch (err) {
      console.error("Failed to toggle lesson:", err.message);
      alert("Could not update lesson status. Please try again.");
    }
  };

  const progressPercentage = lessons.length
    ? (checkedLessons.length / lessons.length) * 100
    : 0;

  // ✅ Download certificate when course completed
  const handleDownloadCertificate = async () => {
    try {
      setLoading(true);
      const response = await generateCertificateAPI({
        courseId: course.id,
        courseTitle: course.title,
        username: course.studentName || "User", // make sure backend knows the logged-in user
      });

      // Create file download
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `${course.title}_certificate.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("Error downloading certificate:", error);
      alert("Could not generate certificate. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page">
      <h2>{course.title}</h2>
      <p className="muted">Instructor: {course.instructor}</p>
      <p>{course.description}</p>

      {/* Progress Bar */}
      {lessons.length > 0 && (
        <>
          <div className="progress-container">
            <div
              className="progress-bar"
              style={{ width: `${progressPercentage}%` }}
            />
          </div>
          <p>{Math.round(progressPercentage)}% Completed</p>
        </>
      )}

      {/* Lessons List */}
      {lessons.length > 0 && (
        <ul className="lessons">
          {lessons.map((lesson) => (
            <li key={lesson.id}>
              <label>
                <input
                  type="checkbox"
                  checked={checkedLessons.includes(lesson.id)}
                  onChange={() => toggleLesson(lesson.id)}
                />
                {lesson.title}
              </label>
            </li>
          ))}
        </ul>
      )}

      {/* ✅ Show Certificate Button only when completed */}
      {progressPercentage === 100 && (
        <button className="btn download-btn" onClick={handleDownloadCertificate} disabled={loading}>
          {loading ? "Generating..." : "Download Certificate"}
        </button>
      )}

      <a className="btn" href="/courses">Back to list</a>
    </div>
  );
}
