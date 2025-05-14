import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./LigaDetalle.css";

// Configuración base para axios
axios.defaults.baseURL = "http://localhost:8080";

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);
    const [mercado, setMercado] = useState(null);

    useEffect(() => {
        // Validar sesión
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) {
            window.location.href = "/login";
            return;
        }

        // Cargar datos de la liga
        axios
            .get(`/api/ligas/${id}`)
            .then((res) => setLiga(res.data))
            .catch((err) => console.error("Error cargando liga:", err));

        // Cargar mercado con lista de pilotos
        axios
            .get(`/api/mercados/liga/${id}`)
            .then((res) => setMercado(res.data))
            .catch((err) => console.error("Error cargando mercado:", err));
    }, [id]);

    if (!liga) {
        return <p className="liga-detalle-container">Cargando liga…</p>;
    }

    return (
        <div className="liga-detalle-container">
            <div className="liga-detalle-panel">
                {/* Encabezado */}
                <div className="liga-detalle-header">Detalles de la Liga</div>

                {/* Sección: Información de la liga */}
                <div className="seccion">
                    <h2>Información de la Liga</h2>
                    <div className="lista-elementos">
                        <div className="elemento">
                            <strong>Nombre:</strong> {liga.nombre}
                        </div>
                        <div className="elemento">
                            <strong>Privada:</strong> {liga.privada ? "Sí" : "No"}
                        </div>
                        <div className="elemento">
                            <strong>Máx. Jugadores:</strong> {liga.maxUsuarios}
                        </div>
                        <div className="elemento">
                            <strong>Jugadores Actuales:</strong> {liga.usuariosIds?.length || 0}
                        </div>
                    </div>
                </div>

                {/* Sección: Mercado */}
                <div className="seccion">
                    <h2>Mercado Actual</h2>
                    <div className="lista-elementos">
                        <div className="elemento">
                            <strong>Fecha:</strong> {mercado?.fecha || "—"}
                        </div>
                        {mercado?.pilotos && mercado.pilotos.length > 0 ? (
                            mercado.pilotos.map((p) => (
                                <div key={p.id} className="elemento">
                                    <div>
                                        <strong>{p.nombreCompleto}</strong>
                                    </div>
                                    <div>
                                        Precio: {p.precio} € {p.fichado && "(Fichado)"}
                                    </div>
                                </div>
                            ))
                        ) : (
                            <div className="elemento">No hay pilotos disponibles en el mercado.</div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LigaDetalle;
