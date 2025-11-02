import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api, { setBasicAuth, clearAuth } from "../api";
import { useAuth } from "../auth";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [err, setErr] = useState("");
    const [loading, setLoading] = useState(false);
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!username || !password) return setErr("Kullanıcı adı/şifre gerekli.");

        setErr("");
        setLoading(true);

        try {
            setBasicAuth(username, password);
            login({ id: 1, username });  // şimdilik hardcoded
            navigate("/home", { replace: true });
        } catch (e) {
            clearAuth();
            setErr("Giriş başarısız");
        }
    };

    return (
        <form onSubmit={handleSubmit} style={{ maxWidth: 360, margin: "40px auto", display: "grid", gap: 8 }}>
            <h2>Giriş yap</h2>
            <input placeholder="Kullanıcı adı" value={username} onChange={e => setUsername(e.target.value)} />
            <input type="password" placeholder="Şifre" value={password} onChange={e => setPassword(e.target.value)} />
            <button disabled={loading || !username || !password}>{loading ? "..." : "Giriş"}</button>
            {err && <div style={{ color: "#b91c1c" }}>{err}</div>}
        </form>
    );
}