import { useEffect, useState } from "react";
import { useAuth } from "../auth";
import api from "../api"; // <-- dosya konumuna göre yol

export default function Home() {


    const { user } = useAuth();
    const userId = user?.id;
    const MAX = 280;

    const [tweets, setTweets] = useState([]);
    const [text, setText] = useState("");
    const [error, setError] = useState("");

    const used = text.length;


    useEffect(() => {
        if (!userId) return;
        (async () => {
            try {
                const { data } = await api.get(`/tweets/users/${userId}`);

                setTweets(Array.isArray(data) ? data : []);
            } catch {
                setError("Tweetler alınamadı");
            }
        })();
    }, [userId]);


    const submitTweet = async (e) => {
        e.preventDefault();
        const content = text.trim();
        if (!content) return;
        setError("");

        try {
            const { data } = await api.post(`/tweets/users/${userId}`, { content });
            setTweets((prev) => [data, ...prev]);
            setText("");
        } catch {
            setError("Tweet atılamadı");
        }
    };

    return (
        <div style={{ maxWidth: 640, margin: "24px auto", fontFamily: "system-ui" }}>
            <h1>Twitter </h1>

            <form onSubmit={submitTweet} style={{ display: "grid", gap: 8, marginBottom: 16 }}>
                <textarea
                    rows={3}
                    placeholder="Neler oluyor?"
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    maxLength={MAX}
                    style={{ padding: 8, border: "1px solid #ddd", borderRadius: 6 }}
                />

                <div style={{ display: "flex", justifyContent: "space-between", fontSize: 12, opacity: 0.8 }}>
                    <span>{text.length}/{MAX}</span>
                    <span>Kalan:  {MAX - text.length}</span>
                </div>

                <button
                    type="submit"
                    disabled={!text.trim()}
                    style={{ padding: "8px 12px", borderRadius: 6 }}
                >
                    Tweetle
                </button>
            </form>

            {error && <div style={{ color: "#b91c1c", marginBottom: 8 }}>{error}</div>}


            <ul>
                {tweets.length === 0 ? (
                    <li key="empty">Henüz tweet yok.</li>
                ) : (
                    tweets.map((t, i) => (
                        <li key={t.id ?? t.createdAt ?? i}>{t.content}</li>
                    ))
                )}
            </ul>
        </div>
    );
}