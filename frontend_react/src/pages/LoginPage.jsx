import { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function LoginPage() {
    const [formData, setFormData] = useState({ email: "", password: "" });
    const [error, setError] = useState("");

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        try {
            const response = await axios.post("http://localhost:8080/api/auth/login", formData);
            const userData = response.data;
            localStorage.setItem("usuario", JSON.stringify(userData)); // Guardar los datos del usuario (por ahora usare esto para la sesion)
            window.location.href = "/hubligas"; // Redirigir al hub tras login
        } catch (err) {
            if (err.response && err.response.data) {
                setError(err.response.data);
            } else {
                setError("Error inesperado");
            }
        }
    };


    return (
        <div className="min-h-screen flex items-center justify-center bg-[#2E3336]">
            <div className="bg-[#1f2224] rounded-2xl shadow-lg p-8 w-full max-w-md border border-[#1a1c1e]">
                <h2 className="text-3xl font-bold text-white text-center mb-6">Fantasy One</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block text-white">Email</label>
                        <input
                            type="email"
                            name="email"
                            onChange={handleChange}
                            required
                            className="w-full px-4 py-2 bg-[#2E3336] text-white border border-[#444] rounded focus:outline-none"
                        />
                    </div>
                    <div>
                        <label className="block text-white">Contraseña</label>
                        <input
                            type="password"
                            name="password"
                            onChange={handleChange}
                            required
                            className="w-full px-4 py-2 bg-[#2E3336] text-white border border-[#444] rounded focus:outline-none"
                        />
                    </div>
                    {error && <p className="text-red-500 text-sm">{error}</p>}
                    <button
                        type="submit"
                        className="w-full py-2 bg-[#D70000] text-white rounded hover:bg-red-700 transition"
                    >
                        Sign In
                    </button>
                </form>
                <p className="text-center text-white text-sm mt-4">
                    ¿No tienes cuenta?{" "}
                    <Link to="/register" className="underline hover:text-gray-300">
                        Regístrate
                    </Link>
                </p>
            </div>
        </div>
    );
}

export default LoginPage;
