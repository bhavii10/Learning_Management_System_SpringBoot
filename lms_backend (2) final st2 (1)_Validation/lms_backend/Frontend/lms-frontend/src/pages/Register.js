import React, { useState } from "react";
import { registerUser } from "../services/api";
import { useNavigate } from "react-router-dom";
import "./auth.css";

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "USER",
  });
  const [msg, setMsg] = useState("");
  const [errors, setErrors] = useState({ password: "" });

  // 🔹 Password Validation
  const validatePassword = (password) => {
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).{8,}$/;
    return regex.test(password)
      ? ""
      : "Password must be 8+ chars, include uppercase, lowercase, number, special char.";
  };

  const onChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
    if (name === "password") {
      setErrors({ password: validatePassword(value) });
    }
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    const passwordError = validatePassword(form.password);
    if (passwordError) return setErrors({ password: passwordError });

    try {
      const res = await registerUser(form);
      localStorage.setItem("user", JSON.stringify(res.data));
      setMsg("Registered successfully!");
      navigate("/login");
    } catch (err) {
      setMsg(err?.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div className="auth-wrapper auth-page">
      {/* 🔹 Illustration Section */}
      <div className="auth-illustration">
        <img src="/images/login-illustration.png" alt="Register" />
      </div>

      {/* 🔹 Form Section */}
      <form className="auth-card" onSubmit={onSubmit}>
        <h2>Create account</h2>

        <label>Name</label>
        <input
          name="name"
          value={form.name}
          onChange={onChange}
          required
        />

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
        {errors.password && <p className="error">{errors.password}</p>}

        <label>Role</label>
        <select name="role" value={form.role} onChange={onChange}>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
        </select>

        <button type="submit">Register</button>

        <p className="hint">
          Already have an account? <a href="/login">Login</a>
        </p>

        {msg && <div className="msg">{msg}</div>}
      </form>
    </div>
  );
}
