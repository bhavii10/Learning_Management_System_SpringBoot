//import React, { useEffect, useState } from "react";
//import { getCourses, deleteCourse } from "../../services/api";
//import "./courses.css";
//
//export default function AdminCourses() {
//  const [courses, setCourses] = useState([]);
//  const load = () => getCourses().then(res => setCourses(res.data));
//  useEffect(() => { load(); }, []);
//
//  const onDelete = async (id) => {
//    if (!window.confirm("Delete this course?")) return;
//    await deleteCourse(id);
//    load();
//  };
//
//  return (
//    <div className="page">
//      <div className="row">
//        <h2>Manage Courses</h2>
//        <a className="btn" href="/admin/courses/new">+ New Course</a>
//      </div>
//      <table className="table">
//        <thead>
//          <tr><th>ID</th><th>Title</th><th>Instructor</th><th>Actions</th></tr>
//        </thead>
//        <tbody>
//          {courses.map(c => (
//            <tr key={c.id}>
//              <td>{c.id}</td>
//              <td>{c.title}</td>
//              <td>{c.instructor}</td>
//              <td className="actions">
//                <a className="btn-secondary" href={`/admin/courses/${c.id}/edit`}>Edit</a>
//                <button className="btn-danger" onClick={() => onDelete(c.id)}>Delete</button>
//              </td>
//            </tr>
//          ))}
//        </tbody>
//      </table>
//    </div>
//  );
//}


import React, { useEffect, useState } from "react";
import {
  getCourses,
  getCourse,      // ✅ fixed import
  deleteCourse,
  updateCourse,
} from "../../services/api";
import "./courses.css";

export default function AdminCourses() {
  const [courses, setCourses] = useState([]);
  const [editingCourse, setEditingCourse] = useState(null); // 👈 track current course being edited
  const [form, setForm] = useState({
    title: "",
    description: "",
    instructor: "",
    lessons: [],
  });

  // Load all courses
  const load = () => getCourses().then((res) => setCourses(res.data));
  useEffect(() => {
    load();
  }, []);

  // Delete a course
  const onDelete = async (id) => {
    if (!window.confirm("Delete this course?")) return;
    await deleteCourse(id);
    load();
  };

  // Edit course (fetch by id)
  const onEdit = async (id) => {
    const res = await getCourse(id); // ✅ corrected from getCourseById
    setEditingCourse(id);
    setForm(res.data); // fill form with course data
  };

  // Lesson handlers
  const handleLessonChange = (index, field, value) => {
    const updatedLessons = [...form.lessons];
    updatedLessons[index][field] = value;
    setForm({ ...form, lessons: updatedLessons });
  };

  const addLesson = () => {
    setForm({
      ...form,
      lessons: [...form.lessons, { title: "", content: "" }],
    });
  };

  const removeLesson = (index) => {
    const updatedLessons = form.lessons.filter((_, i) => i !== index);
    setForm({ ...form, lessons: updatedLessons });
  };

  // Update course
  const handleUpdate = async (e) => {
    e.preventDefault();
    await updateCourse(editingCourse, form);
    setEditingCourse(null);
    load();
  };

  // Cancel editing
  const cancelEdit = () => {
    setEditingCourse(null);
  };

  return (
    <div className="page">
      {!editingCourse ? (
        <>
          <div className="row">
            <h2>Manage Courses</h2>
            <a className="btn" href="/admin/new">
              + New Course
            </a>
          </div>
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Instructor</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {courses.map((c) => (
                <tr key={c.id}>
                  <td>{c.id}</td>
                  <td>{c.title}</td>
                  <td>{c.instructor}</td>
                  <td className="actions">
                    <button
                      className="btn-secondary"
                      onClick={() => onEdit(c.id)}
                    >
                      Edit
                    </button>
                    <button
                      className="btn-danger"
                      onClick={() => onDelete(c.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      ) : (
        <form className="form" onSubmit={handleUpdate}>
          <h2>Edit Course</h2>

          <label>Title</label>
          <input
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
            required
          />

          <label>Description</label>
          <textarea
            value={form.description}
            onChange={(e) =>
              setForm({ ...form, description: e.target.value })
            }
            rows={3}
          />

          <label>Instructor</label>
          <input
            value={form.instructor}
            onChange={(e) =>
              setForm({ ...form, instructor: e.target.value })
            }
            required
          />

          {/* ✅ Lessons Section */}
          <h3>Lessons</h3>
          {form.lessons.map((lesson, index) => (
            <div key={index} className="lesson-form">
              <label>Lesson Title</label>
              <input
                value={lesson.title}
                onChange={(e) =>
                  handleLessonChange(index, "title", e.target.value)
                }
                required
              />

              <label>Lesson Content</label>
              <textarea
                value={lesson.content}
                onChange={(e) =>
                  handleLessonChange(index, "content", e.target.value)
                }
                rows={3}
              />

              <button
                type="button"
                className="btn-secondary"
                onClick={() => removeLesson(index)}
              >
                Remove Lesson
              </button>
            </div>
          ))}

          <button type="button" className="btn" onClick={addLesson}>
            + Add Lesson
          </button>

          <div className="row">
            <button type="submit" className="btn">
              Update
            </button>
            <button
              type="button"
              className="btn-secondary"
              onClick={cancelEdit}
            >
              Cancel
            </button>
          </div>
        </form>
      )}
    </div>
  );
}