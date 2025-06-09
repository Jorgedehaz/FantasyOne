import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function RegisterPage() {
    const navigate = useNavigate();
    const [nombre, setNombre] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [repeatPassword, setRepeatPassword] = useState("");
    const [error, setError] = useState("");

    const validateEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    const validateNombre = (nombre) => /^[a-zA-Z0-9]{3,}$/.test(nombre);

    const handleRegister = async (e) => {
        e.preventDefault();

        if (!validateNombre(nombre)) {
            setError("El nombre debe tener al menos 3 caracteres y solo contener letras o números.");
            return;
        }

        if (!validateEmail(email)) {
            setError("El correo debe tener un formato válido (ej: usuario@correo.com).");
            return;
        }

        if (password !== repeatPassword) {
            setError("Las contraseñas no coinciden.");
            return;
        }

        try {
            await axios.post("http://backend:8080/api/auth/register", {
                nombre,
                email,
                password,
                password2: repeatPassword,
                esAdmin: false,
                activo: true
            });
            navigate("/login");
        } catch (err) {
            if (err.response?.status === 409) {
                setError("El nombre o el correo ya están en uso.");
            } else {
                setError("Error al registrarse. Intenta más tarde.");
            }
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-[#2E3336]">
            <div className="bg-[#1F2325] p-8 rounded-2xl shadow-lg w-96 text-white">
                <h2 className="text-2xl font-bold mb-6 text-center">Registro</h2>
                {error && <p className="text-red-500 mb-4 text-sm text-center">{error}</p>}
                <form onSubmit={handleRegister} className="space-y-4">
                    <input
                        type="text"
                        placeholder="Nombre"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        className="w-full p-2 rounded bg-[#2E3336] border border-gray-700 text-white"
                    />
                    <input
                        type="email"
                        placeholder="Correo electrónico"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="w-full p-2 rounded bg-[#2E3336] border border-gray-700 text-white"
                    />
                    <input
                        type="password"
                        placeholder="Contraseña"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="w-full p-2 rounded bg-[#2E3336] border border-gray-700 text-white"
                    />
                    <input
                        type="password"
                        placeholder="Repetir contraseña"
                        value={repeatPassword}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                        className="w-full p-2 rounded bg-[#2E3336] border border-gray-700 text-white"
                    />
                    <button
                        type="submit"
                        className="w-full bg-[#D70000] hover:bg-red-700 transition-colors text-white font-semibold py-2 rounded"
                    >
                        Registrarse
                    </button>
                </form>
                <p className="text-sm text-center mt-4">
                    ¿Ya tienes cuenta?{' '}
                    <button
                        onClick={() => navigate("/login")}
                        className="text-[#D70000] hover:underline"
                    >
                        Sign In
                    </button>
                </p>
            </div>
        </div>
    );
}
