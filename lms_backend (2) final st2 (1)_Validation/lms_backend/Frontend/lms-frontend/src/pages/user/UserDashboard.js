import React, { useEffect, useState } from "react";
import "./UserDashboard.css";

export default function UserDashboard() {
  const [announcements, setAnnouncements] = useState([]);

  useEffect(() => {
    const fetchAnnouncements = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/announcements");
        if (!res.ok) throw new Error("Failed to fetch announcements");
        const data = await res.json();
        setAnnouncements(Array.isArray(data) ? data : []);
      } catch (err) {
        console.error("Error:", err);
      }
    };
    fetchAnnouncements();
  }, []);

  return (
    <div className="user-dashboard">
      <h2>User Dashboard - Announcements</h2>
      {announcements.length === 0 ? (
        <p>No announcements available.</p>
      ) : (
        <ul className="announcement-list">
          {announcements.map((a) => (
            <li key={a.id || a.announcementId}>
              <h3>{a.title}</h3>
              <p>{a.message || a.content}</p>
              <small>
                Posted on:{" "}
                {a.createdAt ? new Date(a.createdAt).toLocaleString() : "N/A"}
              </small>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}