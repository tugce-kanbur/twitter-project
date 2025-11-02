import { Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./auth";
import Login from "./pages/Login";
import Home from "./pages/Home";
import PrivateRoute from "./PrivateRoute";
import { useEffect } from "react";
import api, { setBasicAuth } from "./api";

export default function App() {
  useEffect(() => {

    setBasicAuth("kullaniciAdiVeyaEmail", "düzParola");


    api.get("/tweets/users/1")
      .then(res => console.log("OK:", res.status))
      .catch(err => console.error("ERR:", err?.response?.status));
  }, []);
  return (
    <AuthProvider>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={
          <PrivateRoute><Home /></PrivateRoute>
        } />
        <Route path="*" element={<Navigate to="/home" replace />} />
      </Routes>
    </AuthProvider>
  );
}
