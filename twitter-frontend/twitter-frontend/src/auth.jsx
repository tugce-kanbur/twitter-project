import { createContext, useContext, useEffect, useState } from "react";

const AuthCtx = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);


    useEffect(() => {
        const raw = localStorage.getItem("auth_user");
        if (raw) {
            try { setUser(JSON.parse(raw)); }
            catch (err) {
                console.error("auth_user JSON parse hatası:", err);
                return null;
            }
        }
    }, []);

    const login = (u) => {
        setUser(u);
        localStorage.setItem("auth_user", JSON.stringify(u));
    };

    const logout = () => {
        setUser(null);
        localStorage.removeItem("auth_user");
    };

    return (
        <AuthCtx.Provider value={{ user, login, logout }}>
            {children}
        </AuthCtx.Provider>
    );
}

export function useAuth() {
    return useContext(AuthCtx);
}