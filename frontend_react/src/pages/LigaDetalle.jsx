import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./LigaDetalle.css";
import PilotoDetalle from './PilotoDetalle';

// Base URL
axios.defaults.baseURL = "http://localhost:8080";

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);
    const [mercado, setMercado] = useState(null);
    const [equipo, setEquipo] = useState(null);
    const [clasificacion, setClasificacion] = useState([]);
    const [selectedPilot, setSelectedPilot] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [loadingFichar, setLoadingFichar] = useState(false);

    // Abre modal
    const handlePilotClick = (piloto) => {
        setSelectedPilot(piloto);
        setShowModal(true);
    };
    const closeModal = () => setShowModal(false);

    // Fichar piloto
    const handleFichar = (piloto) => {
        if (!equipo) return;
        if (equipo.pilotos.length >= 2) {
            alert("Ya tienes 2 pilotos fichados.");
            return;
        }
        setLoadingFichar(true);
        axios.post(
            `/api/equipos/${equipo.id}/fichar`,
            null,
            { params: { pilotoId: piloto.id } }
        )
            .then(res => {
                // equipo actualizado
                setEquipo(res.data);
                // retiramos al piloto del mercado para no poder ficharlo otra vez
                setMercado(prev => ({
                    ...prev,
                    pilotos: prev.pilotos.filter(p => p.id !== piloto.id)
                }));
                // recargamos clasificación
                return axios.get(`/api/equipos/clasificacion/${id}`);
            })
            .then(res => setClasificacion(res.data))
            .catch(err => {
                if (err.response && err.response.data && err.response.data.message) {
                    alert(err.response.data.message);
                } else {
                    console.error("Error fichando piloto:", err);
                    alert("Ha ocurrido un error inesperado");
                }
            })
            .finally(() => setLoadingFichar(false));
    };

    // Recalcular puntos al entrar (opcional)
    useEffect(() => {
        axios.post(`/api/resultados/recalcular-puntos`)
            .catch(err => console.error("Error recalculando puntos:", err));
    }, [id]);

    // Carga inicial de datos
    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) return window.location.href = "/login";
        const usuarioId = usuario.id;

        axios.get(`/api/ligas/${id}`)
            .then(res => setLiga(res.data))
            .catch(err => console.error("Error cargando liga:", err));

        axios.get(`/api/mercados/liga/${id}`)
            .then(res => setMercado(res.data))
            .catch(err => console.error("Error cargando mercado:", err));

        axios.get(`/api/equipos/usuario/${usuarioId}/liga/${id}`)
            .then(res => setEquipo(res.data))
            .catch(err => console.error("Error cargando equipo:", err));

        axios.get(`/api/equipos/clasificacion/${id}`)
            .then(res => setClasificacion(res.data))
            .catch(err => console.error("Error cargando clasificación:", err));
    }, [id]);

    if (!liga) return <p className="liga-detalle-container">Cargando liga…</p>;

    return (
        <div className="liga-detalle-container">
            <div className="liga-detalle-header-buttons">
                <button className="crear-liga-btn" onClick={() => alert('Perfil (no implementado aún)')}>
                    Mi Perfil
                </button>
                <button className="crear-liga-btn" onClick={() => window.location.href = '/hubligas'}>
                    Volver
                </button>
            </div>
            <h2 className="liga-detalle-header">Liga: {liga.nombre}</h2>
            <div className="liga-detalle-layout">
                {/* IZQUIERDA: Mi Equipo + Clasificación */}
                <div className="liga-detalle-left">
                    <section className="seccion equipo-section">
                        <h3>Mi Equipo</h3>
                        {equipo ? (
                            <>
                                <div className="equipo-resumen">
                                    <p><strong>Presupuesto:</strong> {equipo.monedas} €</p>
                                    <p><strong>Puntos:</strong> {equipo.puntosAcumulados}</p>
                                </div>
                                <ul className="equipo-lista">
                                    {equipo.pilotos.map(p => (
                                        <li key={p.id} className="equipo-piloto-item" onClick={() => handlePilotClick(p)}>
                                            <img src={p.imagenUrl} alt={p.nombreCompleto} className="equipo-piloto-img" />
                                            <div className="equipo-piloto-datos">
                                                <div className="piloto-datos">
                                                    <span className="piloto-nombre">{p.nombreCompleto}</span>
                                                    <span className="piloto-puntos">{p.puntosFantasy} pts</span>
                                                </div>
                                                <span className="piloto-precio">{p.precio} €</span>
                                            </div>
                                        </li>
                                    ))}
                                    {equipo.pilotos.length === 0 && <li>No tienes pilotos fichados aún.</li>}
                                </ul>
                            </>
                        ) : <p>Cargando equipo…</p>}
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
                        ) : <p>Calculando clasificación…</p>}
                    </section>
                </div>

                {/* DERECHA: Mercado */}
                <div className="liga-detalle-right">
                    <section className="seccion mercado-section">
                        <h3>Mercado</h3>
                        <p><strong>Fecha:</strong> {mercado?.fecha || '—'}</p>
                        {mercado?.pilotos?.length > 0 ? (
                            <ul className="pilotos-lista">
                                {mercado.pilotos.map(p => (
                                    <li
                                        key={p.id}
                                        className="piloto-item"
                                        onClick={() => handlePilotClick(p)}
                                    >
                                        <img src={p.imagenUrl} alt={p.nombreCompleto} className="piloto-imagen" />
                                        <div className="piloto-datos">
                                            <span className="piloto-nombre">{p.nombreCompleto}</span>
                                            <span className="piloto-puntos">{p.puntosFantasy} pts</span>
                                        </div>
                                        <span className="piloto-precio">{p.precio} €</span>
                                        <button
                                            className="fichar-button"
                                            disabled={loadingFichar || (equipo?.pilotos.length >= 2)}
                                            onClick={e => {
                                                e.stopPropagation();
                                                handleFichar(p);
                                            }}
                                        >
                                            {loadingFichar ? '...' : 'Fichar'}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <p>No hay pilotos disponibles en el mercado.</p>
                        )}
                    </section>
                </div>
            </div>

            {/* Modal Detalle de Piloto */}
            {showModal && selectedPilot && (
                <PilotoDetalle
                    piloto={selectedPilot}
                    show={showModal}
                    onClose={closeModal}
                />
            )}
        </div>
    );
};

export default LigaDetalle;
