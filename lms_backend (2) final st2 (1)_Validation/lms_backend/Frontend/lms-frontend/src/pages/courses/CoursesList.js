import React, { useEffect, useState } from "react";
import { getCourses } from "../../services/api";
import "./courses.css";

export default function CoursesList() {
  const [courses, setCourses] = useState([]);

  useEffect(() => {
    getCourses().then(res => setCourses(res.data)).catch(() => setCourses([]));
  }, []);

  return (
    <div className="page">
      <h2>All Courses</h2>
      <div className="grid">
        {courses.map(c => (
          <a key={c.id} className="card" href={`/courses/${c.id}`}>
            <h3>{c.title}</h3>
            <p className="muted">By {c.instructor}</p>
            <p className="desc">{c.description?.slice(0, 120) || "—"}</p>
          </a>
        ))}
      </div>
    </div>
  );
}