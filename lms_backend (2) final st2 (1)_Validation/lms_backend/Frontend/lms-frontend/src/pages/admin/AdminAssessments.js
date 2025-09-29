//import React, { useEffect, useState } from "react";
//import { Link } from "react-router-dom";
//import { getCourses, getAssessmentsByCourse, createAssessment } from "../../services/api";
//import "./AdminAssessments.css";
//
//export default function AdminAssessments() {
//  const [courses, setCourses] = useState([]);
//  const [selectedCourse, setSelectedCourse] = useState(null);
//  const [assessments, setAssessments] = useState([]);
//  const [title, setTitle] = useState("");
//
//  useEffect(() => {
//    getCourses().then(res => setCourses(res.data)).catch(err => console.error(err));
//  }, []);
//
//  const loadAssessments = (courseId) => {
//    if (!courseId) return;
//    setSelectedCourse(Number(courseId));
//    getAssessmentsByCourse(Number(courseId))
//      .then(res => setAssessments(res.data))
//      .catch(err => console.error(err));
//  };
//
//  const handleAdd = async () => {
//    if (!selectedCourse || !title) return;
//    await createAssessment({ courseId: selectedCourse, title });
//    setTitle("");
//    loadAssessments(selectedCourse);
//  };
//
//  return (
//    <div className="admin-assessments-container">
//      <h2>Manage Assessments</h2>
//
//      <select
//        className="course-select"
//        onChange={(e) => loadAssessments(e.target.value)}
//        value={selectedCourse || ""}
//      >
//        <option value="">Select Course</option>
//        {courses.map(c => (
//          <option key={c.id} value={c.id}>{c.title}</option>
//        ))}
//      </select>
//
//      {selectedCourse && (
//        <>
//          <h3>Assessments</h3>
//          <ul className="assessments-list">
//            {assessments.map(a => (
//              <li className="assessment-item" key={a.id}>
//                <span>{a.title}</span>
//                <Link className="manage-link" to={`/admin/assessments/${a.id}/questions`}>
//                  Manage Questions
//                </Link>
//              </li>
//            ))}
//          </ul>
//
//          <div className="add-assessment">
//            <input
//              placeholder="New Assessment Title"
//              value={title}
//              onChange={e => setTitle(e.target.value)}
//            />
//            <button onClick={handleAdd}>Add Assessment</button>
//          </div>
//        </>
//      )}
//    </div>
//  );
//}




import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
  getCourses,
  getAssessmentsByCourse,
  createAssessment,
  updateAssessment,
  deleteAssessment,
} from "../../services/api";
import "./AdminAssessments.css";

export default function AdminAssessments() {
  const [courses, setCourses] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [assessments, setAssessments] = useState([]);
  const [title, setTitle] = useState("");
  const [editingId, setEditingId] = useState(null);
  const [editTitle, setEditTitle] = useState("");

  useEffect(() => {
    getCourses().then((res) => setCourses(res.data)).catch(console.error);
  }, []);

  const loadAssessments = (courseId) => {
    if (!courseId) return;
    setSelectedCourse(Number(courseId));
    getAssessmentsByCourse(Number(courseId))
      .then((res) => setAssessments(res.data))
      .catch(console.error);
  };

  const handleAdd = async () => {
    if (!selectedCourse || !title) return;
    await createAssessment({ courseId: selectedCourse, title });
    setTitle("");
    loadAssessments(selectedCourse);
  };

  const handleEdit = (a) => {
    setEditingId(a.id);
    setEditTitle(a.title);
  };

  const handleUpdate = async (id) => {
    if (!editTitle) return;
    await updateAssessment(id, { courseId: selectedCourse, title: editTitle });
    setEditingId(null);
    setEditTitle("");
    loadAssessments(selectedCourse);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this assessment?")) {
      await deleteAssessment(id);
      loadAssessments(selectedCourse);
    }
  };

  return (
    <div className="admin-assessments-container">
      <h2>Manage Assessments</h2>

      <select
        className="course-select"
        onChange={(e) => loadAssessments(e.target.value)}
        value={selectedCourse || ""}
      >
        <option value="">Select Course</option>
        {courses.map((c) => (
          <option key={c.id} value={c.id}>
            {c.title}
          </option>
        ))}
      </select>

      {selectedCourse && (
        <>
          <h3>Assessments</h3>
          <ul className="assessments-list">
            {assessments.map((a) => (
              <li className="assessment-item" key={a.id}>
                {editingId === a.id ? (
                  <>
                    <input
                      value={editTitle}
                      onChange={(e) => setEditTitle(e.target.value)}
                    />
                    <button onClick={() => handleUpdate(a.id)}>Save</button>
                    <button onClick={() => setEditingId(null)}>Cancel</button>
                  </>
                ) : (
                  <>
                    <span>{a.title}</span>
                    <div className="assessment-actions">
                      <Link
                        className="manage-link"
                        to={`/admin/assessments/${a.id}/questions`}
                      >
                        Manage Questions
                      </Link>
                      <button
                        className="edit-btn"
                        onClick={() => handleEdit(a)}
                      >
                        Edit
                      </button>
                      <button
                        className="delete-btn"
                        onClick={() => handleDelete(a.id)}
                      >
                        Delete
                      </button>
                    </div>
                  </>
                )}
              </li>
            ))}
          </ul>

          <div className="add-assessment">
            <input
              placeholder="New Assessment Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
            <button onClick={handleAdd}>Add Assessment</button>
          </div>
        </>
      )}
    </div>
  );
}
