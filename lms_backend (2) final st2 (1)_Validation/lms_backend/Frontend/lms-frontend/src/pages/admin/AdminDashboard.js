import React, { useEffect, useState } from "react";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [announcements, setAnnouncements] = useState([]);
  const [newAnnouncement, setNewAnnouncement] = useState({
    title: "",
    content: "",
    author: "Admin",
    pinned: false,
    startDate: "",
    endDate: "",
  });
  const [editId, setEditId] = useState(null);

  // Fetch announcements
  const fetchAnnouncements = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/announcements");
      if (!res.ok) throw new Error("Failed to fetch announcements");
      const data = await res.json();
      setAnnouncements(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Error fetching:", err);
    }
  };

  useEffect(() => {
    fetchAnnouncements();
  }, []);

  // Create / Update
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const method = editId ? "PUT" : "POST";
      const url = editId
        ? `http://localhost:8080/api/announcements/${editId}`
        : "http://localhost:8080/api/announcements";

      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newAnnouncement),
      });

      if (!res.ok) throw new Error("Failed to save announcement");

      await fetchAnnouncements();
      setNewAnnouncement({
        title: "",
        content: "",
        author: "Admin",
        pinned: false,
        startDate: "",
        endDate: "",
      });
      setEditId(null);
    } catch (err) {
      console.error("Error saving:", err);
    }
  };

  // Delete
  const handleDelete = async (id) => {
    try {
      const res = await fetch(
        `http://localhost:8080/api/announcements/${id}`,
        { method: "DELETE" }
      );
      if (!res.ok) throw new Error("Failed to delete");
      await fetchAnnouncements();
    } catch (err) {
      console.error("Error deleting:", err);
    }
  };

  // Edit
  const handleEdit = (a) => {
    setNewAnnouncement({
      title: a.title,
      content: a.content,
      author: a.author || "Admin",
      pinned: a.pinned || false,
      startDate: a.startDate ? a.startDate.slice(0, 16) : "",
      endDate: a.endDate ? a.endDate.slice(0, 16) : "",
    });
    setEditId(a.id || a.announcementId);
  };

  return (
    <div className="admin-dashboard">
      <h2>Admin Dashboard - Announcements</h2>

      {/* Create / Edit Form */}
      <form onSubmit={handleSubmit} className="announcement-form">
        <input
          type="text"
          placeholder="Title"
          value={newAnnouncement.title}
          onChange={(e) =>
            setNewAnnouncement({ ...newAnnouncement, title: e.target.value })
          }
          required
        />
        <textarea
          placeholder="Message"
          value={newAnnouncement.content}
          onChange={(e) =>
            setNewAnnouncement({ ...newAnnouncement, content: e.target.value })
          }
          required
        />
        <input
          type="datetime-local"
          value={newAnnouncement.startDate}
          onChange={(e) =>
            setNewAnnouncement({ ...newAnnouncement, startDate: e.target.value })
          }
          required
        />
        <input
          type="datetime-local"
          value={newAnnouncement.endDate}
          onChange={(e) =>
            setNewAnnouncement({ ...newAnnouncement, endDate: e.target.value })
          }
          required
        />
        <label>
          <input
            type="checkbox"
            checked={newAnnouncement.pinned}
            onChange={(e) =>
              setNewAnnouncement({ ...newAnnouncement, pinned: e.target.checked })
            }
          />{" "}
          Pinned
        </label>
        <button type="submit">{editId ? "Update" : "Create"}</button>
      </form>

      {/* List */}
      <ul className="announcement-list">
        {announcements.map((a) => (
          <li key={a.id || a.announcementId}>
            <h3>{a.title}</h3>
            <p>{a.content}</p>
            <small>
              Author: {a.author} | Pinned: {a.pinned ? "Yes" : "No"}
              <br />
              Start: {a.startDate ? new Date(a.startDate).toLocaleString() : "N/A"}
              <br />
              End: {a.endDate ? new Date(a.endDate).toLocaleString() : "N/A"}
              <br />
              Created At:{" "}
              {a.createdAt ? new Date(a.createdAt).toLocaleString() : "N/A"}
            </small>
            <div className="actions">
              <button onClick={() => handleEdit(a)}>Edit</button>
              <button onClick={() => handleDelete(a.id || a.announcementId)}>
                Delete
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
