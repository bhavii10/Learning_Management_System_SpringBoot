import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getQuestionsForAssessmentAdmin, createQuestion } from "../../services/api";
import "./ManageQuestions.css";   // ✅ CSS import

export default function ManageQuestions() {
  const { assessmentId } = useParams();
  const [questions, setQuestions] = useState([]);
  const [newQ, setNewQ] = useState({ questionText: "", options: "", correctAnswer: "" });

  useEffect(() => {
    if (assessmentId) {
      getQuestionsForAssessmentAdmin(assessmentId)
        .then(res => setQuestions(res.data))
        .catch(err => console.error("Error fetching questions:", err));
    }
  }, [assessmentId]);

  const handleAdd = async () => {
    if (!assessmentId) return;

    await createQuestion({
      assessmentId: Number(assessmentId),
      questionText: newQ.questionText,
      options: newQ.options.split(",").map(opt => opt.trim()),
      correctAnswer: newQ.correctAnswer
    });

    setNewQ({ questionText: "", options: "", correctAnswer: "" });

    getQuestionsForAssessmentAdmin(assessmentId).then(res => setQuestions(res.data));
  };

  return (
    <div className="manage-questions-container">
      <h2>Manage Questions (Assessment {assessmentId})</h2>

      <ul>
        {questions.map(q => (
          <li key={q.id}>
            {q.questionText} (Ans: {q.correctAnswer})
          </li>
        ))}
      </ul>

      <h3>Add Question</h3>
      <input
        placeholder="Question"
        value={newQ.questionText}
        onChange={e => setNewQ({ ...newQ, questionText: e.target.value })}
      />
      <input
        placeholder="Options (comma separated)"
        value={newQ.options}
        onChange={e => setNewQ({ ...newQ, options: e.target.value })}
      />
      <input
        placeholder="Correct Answer"
        value={newQ.correctAnswer}
        onChange={e => setNewQ({ ...newQ, correctAnswer: e.target.value })}
      />
      <button onClick={handleAdd}>Add</button>
    </div>
  );
}