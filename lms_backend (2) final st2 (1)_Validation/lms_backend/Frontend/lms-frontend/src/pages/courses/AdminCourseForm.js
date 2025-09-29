//import React, { useEffect, useState } from "react";
//import { createCourse, getCourse, updateCourse } from "../../services/api";
//import { useNavigate, useParams } from "react-router-dom";
//import "./courses.css";
//
//export default function AdminCourseForm() {
//  const navigate = useNavigate();
//  const { id } = useParams(); // undefined for new
//  const editing = Boolean(id);
//
//  const [form, setForm] = useState({ title: "", description: "", instructor: "" });
//  const [msg, setMsg] = useState("");
//
//  useEffect(() => {
//    if (editing) {
//      getCourse(id).then(res => setForm({
//        title: res.data.title || "",
//        description: res.data.description || "",
//        instructor: res.data.instructor || ""
//      }));
//    }
//  }, [editing, id]);
//
//  const onChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));
//
//  const onSubmit = async (e) => {
//    e.preventDefault();
//    setMsg("");
//    try {
//      if (editing) {
//        await updateCourse(id, form);
//      } else {
//        await createCourse(form);
//      }
//      navigate("/admin/courses");
//    } catch (err) {
//      const m = err?.response?.data?.message || "Save failed";
//      setMsg(m);
//    }
//  };
//
//  return (
//    <div className="page">
//      <h2>{editing ? "Edit Course" : "New Course"}</h2>
//      <form className="form" onSubmit={onSubmit}>
//        <label>Title</label>
//        <input name="title" value={form.title} onChange={onChange} required />
//
//        <label>Description</label>
//        <textarea name="description" value={form.description} onChange={onChange} rows={5} />
//
//        <label>Instructor (name/email)</label>
//        <input name="instructor" value={form.instructor} onChange={onChange} required />
//
//        <div className="row">
//          <button className="btn" type="submit">{editing ? "Update" : "Create"}</button>
//          <a className="btn-secondary" href="/admin/courses">Cancel</a>
//        </div>
//
//        {msg && <div className="msg">{msg}</div>}
//      </form>
//    </div>
//  );
//}


import React, { useEffect, useState } from "react";
import { createCourse, getCourse, updateCourse } from "../../services/api";
import { useNavigate, useParams } from "react-router-dom";
import "./courses.css";

export default function AdminCourseForm() {
  const navigate = useNavigate();
  const { id } = useParams(); // undefined for new course
  const editing = Boolean(id);

  const [form, setForm] = useState({
    title: "",
    description: "",
    instructor: "",
    lessons: [], // ✅ store lessons also
  });
  const [msg, setMsg] = useState("");

  useEffect(() => {
    if (editing) {
      getCourse(id).then((res) =>
        setForm({
          title: res.data.title || "",
          description: res.data.description || "",
          instructor: res.data.instructor || "",
          lessons: res.data.lessons || [],
        })
      );
    }
  }, [editing, id]);

  const onChange = (e) =>
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));

  // ✅ handle lessons changes
  const handleLessonChange = (index, field, value) => {
    const updatedLessons = [...form.lessons];
    updatedLessons[index][field] = value;
    setForm((f) => ({ ...f, lessons: updatedLessons }));
  };

  const addLesson = () => {
    setForm((f) => ({
      ...f,
      lessons: [...f.lessons, { title: "", content: "" }],
    }));
  };

  const removeLesson = (index) => {
    const updatedLessons = [...form.lessons];
    updatedLessons.splice(index, 1);
    setForm((f) => ({ ...f, lessons: updatedLessons }));
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    setMsg("");
    try {
      if (editing) {
        await updateCourse(id, form);
      } else {
        await createCourse(form);
      }
      navigate("/admin/courses");
    } catch (err) {
      const m = err?.response?.data?.message || "Save failed";
      setMsg(m);
    }
  };

  return (
    <div className="page">
      <h2>{editing ? "Edit Course" : "New Course"}</h2>
      <form className="form" onSubmit={onSubmit}>
        <label>Title</label>
        <input
          name="title"
          value={form.title}
          onChange={onChange}
          required
        />

        <label>Description</label>
        <textarea
          name="description"
          value={form.description}
          onChange={onChange}
          rows={5}
        />

        <label>Instructor (name/email)</label>
        <input
          name="instructor"
          value={form.instructor}
          onChange={onChange}
          required
        />

        {/* ✅ Lesson Section */}
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
          <button className="btn" type="submit">
            {editing ? "Update" : "Create"}
          </button>
          <a className="btn-secondary" href="/admin/courses">
            Cancel
          </a>
        </div>

        {msg && <div className="msg">{msg}</div>}
      </form>
    </div>
  );
}
