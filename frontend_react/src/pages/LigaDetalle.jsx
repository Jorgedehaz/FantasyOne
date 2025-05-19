import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./LigaDetalle.css";

// Base URL
axios.defaults.baseURL = "http://localhost:8080";

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);
    const [mercado, setMercado] = useState(null);
    const [equipo, setEquipo] = useState(null);
    const [clasificacion, setClasificacion] = useState([]);

    useEffect(() => {
        // Sesión
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) {
            window.location.href = "/login";
            return;
        }
        const usuarioId = usuario.id;

        // Liga
        axios
            .get(`/api/ligas/${id}`)
            .then((res) => setLiga(res.data))
            .catch((err) => console.error("Error cargando liga:", err));

        // Mercado
        axios
            .get(`/api/mercados/liga/${id}`)
            .then((res) => setMercado(res.data))
            .catch((err) => console.error("Error cargando mercado:", err));

        // Equipo del usuario
        axios
            .get(`/api/equipos/usuario/${usuarioId}/liga/${id}`)
            .then((res) => setEquipo(res.data))
            .catch((err) => console.error("Error cargando equipo:", err));

        // Clasificación de la liga
        axios
            .get(`/api/equipos/clasificacion/${id}`)
            .then((res) => setClasificacion(res.data))
            .catch((err) => console.error("Error cargando clasificación:", err));
    }, [id]);

    if (!liga) {
        return <p className="liga-detalle-container">Cargando liga…</p>;
    }

    return (
        <div className="liga-detalle-container">
            <h2 className="liga-detalle-header">Liga: {liga.nombre}</h2>
            <div className="liga-detalle-layout">
                {/* Izquierda: Equipo y Clasificación */}
                <div className="liga-detalle-left">
                    <section className="seccion equipo-section">
                        <h3>Mi Equipo</h3>
                        {equipo ? (
                            <ul className="equipo-lista">
                                {equipo.pilotos.map((p) => (
                                    <li key={p.id} className="equipo-piloto-item">
                                        <img src={p.imagenUrl} alt={p.nombreCompleto} className="equipo-piloto-img" />
                                        <div>
                                            <span>{p.nombreCompleto}</span>
                                            <span>{p.precio} €</span>
                                        </div>
                                    </li>
                                ))}
                                {equipo.pilotos.length === 0 && (
                                    <li>No tienes pilotos fichados aún.</li>
                                )}
                            </ul>
                        ) : (
                            <p>Cargando equipo…</p>
                        )}
                    </section>

                    <section className="seccion clasificacion-section">
                        <h3>Clasificación</h3>
                        {clasificacion.length > 0 ? (
                            <table className="clasificacion-table">
                                <thead>
                                <tr>
                                    <th>Pos</th>
                                    <th>Usuario</th>
                                    <th>Puntos</th>
                                </tr>
                                </thead>
                                <tbody>
                                {clasificacion.map((e, idx) => (
                                    <tr key={e.id} className={e.id === equipo?.id ? 'mi-equipo-fila' : ''}>
                                        <td>{idx + 1}</td>
                                        <td>{e.nombreUsuario}</td>
                                        <td>{e.puntosAcumulados}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        ) : (
                            <p>Calculando clasificación…</p>
                        )}
                    </section>
                </div>

                {/* Derecha: Mercado */}
                <div className="liga-detalle-right">
                    <section className="seccion mercado-section">
                        <h3>Mercado Actual</h3>
                        <p><strong>Fecha:</strong> {mercado?.fecha || '—'}</p>
                        {mercado?.pilotos && mercado.pilotos.length > 0 ? (
                            <ul className="pilotos-lista">
                                {mercado.pilotos.map((p) => (
                                    <li key={p.id} className="piloto-item">
                                        <img src={p.imagenUrl} alt={p.nombreCompleto} className="piloto-imagen" />
                                        <div className="piloto-datos">
                                            <span className="piloto-nombre">{p.nombreCompleto}</span>
                                            <span className="piloto-precio">{p.precio} €</span>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No hay pilotos disponibles en el mercado.</p>
                        )}
                    </section>
                </div>
            </div>
        </div>
    );
};

export default LigaDetalle;
