import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:3200/",
});


export function setBasicAuth(username, password) {
    const token = btoa(`${username}:${password}`);
    api.defaults.headers.common.Authorization = `Basic ${token}`;
}
export function clearAuth() {
    delete api.defaults.headers.common.Authorization;
}

export default api;