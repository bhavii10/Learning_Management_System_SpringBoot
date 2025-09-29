import React, { useState } from "react";
import { loginUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import "./auth.css";

export default function Login() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });
  const [msg, setMsg] = useState("");

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setMsg("");
    try {
      const res = await loginUser(form);
      localStorage.setItem("user", JSON.stringify(res.data));
      navigate("/");
    } catch (err) {
      setMsg(err?.response?.data?.message || "Invalid email or password");
    }
  };

  return (
    <div className="auth-wrapper auth-page">
      {/* 🔹 Illustration Section */}
      <div className="auth-illustration">
        <img src="/images/login-illustration.png" alt="Login" />
      </div>

      {/* 🔹 Form Section */}
      <form className="auth-card" onSubmit={onSubmit}>
        <h2>Welcome back</h2>

        <label>Email</label>
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={onChange}
          required
        />

        <label>Password</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={onChange}
          required
        />

        <button type="submit">Login</button>

        <p className="hint">
          New here? <a href="/register">Create an account</a>
        </p>

        {msg && <div className="msg">{msg}</div>}
      </form>
    </div>
  );
}
