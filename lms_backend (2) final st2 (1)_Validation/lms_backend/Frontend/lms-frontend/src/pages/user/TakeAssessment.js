//import React, { useEffect, useState } from "react";
//import { useParams } from "react-router-dom";
//import { getQuestionsForAssessment, submitResult } from "../../services/api";
//import "./TakeAssessment.css";
//
//export default function TakeAssessment() {
//  const { assessmentId } = useParams();
//  const [questions, setQuestions] = useState([]);
//  const [answers, setAnswers] = useState({});
//  const [result, setResult] = useState(null);
//
//  const user = JSON.parse(localStorage.getItem("user"));
//
//  useEffect(() => {
//    getQuestionsForAssessment(assessmentId).then(res => setQuestions(res.data));
//  }, [assessmentId]);
//
//  const handleSubmit = async () => {
//    const payload = {
//      assessmentId: Number(assessmentId),
//      userId: user.id,
//      answers
//    };
//    const res = await submitResult(payload);
//    setResult(res.data);
//  };
//
//  if (result) {
//    return (
//      <div className="result-box">
//        <h2>Result</h2>
//        <p>Score: {result.score} / {result.totalMarks}</p>
//      </div>
//    );
//  }
//
//  return (
//    <div className="take-assessment">
//      <h2>Take Assessment</h2>
//      {questions.map(q => (
//        <div key={q.id} className="question-card">
//          <p>{q.questionText}</p>
//          {q.options.map(opt => (
//            <label key={opt} className="option">
//              <input
//                type="radio"
//                name={q.id}
//                value={opt}
//                onChange={() => setAnswers({ ...answers, [q.id]: opt })}
//              />
//              {opt}
//            </label>
//          ))}
//        </div>
//      ))}
//      <button className="submit-btn" onClick={handleSubmit}>
//        Submit
//      </button>
//    </div>
//  );
//}


import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getQuestionsForAssessment, submitResult } from "../../services/api";
import "./TakeAssessment.css";

export default function TakeAssessment() {
  const { assessmentId } = useParams();
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState(null);

  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    getQuestionsForAssessment(assessmentId).then((res) => setQuestions(res.data));
  }, [assessmentId]);

  const handleSubmit = async () => {
    const payload = {
      assessmentId: Number(assessmentId),
      userId: user.id,
      answers,
    };
    const res = await submitResult(payload);
    setResult(res.data);
  };

  if (result) {
    return (
      <div className="result-box">
        <h2>Result</h2>
        <p>
          Score: {result.score} / {result.totalMarks}
        </p>
      </div>
    );
  }

  return (
    <div className="take-assessment">
      <h2>Take Assessment</h2>
      {questions.map((q) => (
        <div key={q.id} className="question-card">
          <p>{q.questionText}</p>
          {q.options.map((opt) => (
            <label key={opt} className="option">
              <input
                type="radio"
                name={q.id}
                value={opt}
                onChange={() => setAnswers({ ...answers, [q.id]: opt })}
              />
              {opt}
            </label>
          ))}
        </div>
      ))}
      <button className="submit-btn" onClick={handleSubmit}>
        Submit
      </button>
    </div>
  );
}
