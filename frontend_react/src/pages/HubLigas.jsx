import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './HubLigas.css';

const HubLigas = () => {
    const [ligasPublicas, setLigasPublicas] = useState([]);
    const [ligasPrivadas, setLigasPrivadas] = useState([]);
    const [filtroPublicas, setFiltroPublicas] = useState('');
    const [filtroPrivadas, setFiltroPrivadas] = useState('');
    const [mostrarModal, setMostrarModal] = useState(false);
    const [nuevaLiga, setNuevaLiga] = useState({ nombre: '', privada: false, maxUsuarios: 10, codigoAcceso: '' });
    const [mostrarUnirsePrivada, setMostrarUnirsePrivada] = useState(false);
    const [datosUnirsePrivada, setDatosUnirsePrivada] = useState({ nombreLiga: '', claveAcceso: '' });

    const usuario = JSON.parse(localStorage.getItem('usuario'));
    const usuarioId = usuario?.id;
    const navigate = useNavigate();

    useEffect(() => {
        if (!usuarioId) return;

        axios.get(`http://localhost:8080/api/ligas/privadas?esPrivada=false`)
            .then(res => setLigasPublicas(res.data))
            .catch(err => console.error('Error fetching public leagues:', err));

        axios.get(`http://localhost:8080/api/ligas/privadas/${usuarioId}`)
            .then(res => setLigasPrivadas(res.data))
            .catch(err => console.error('Error fetching private leagues:', err));
    }, [usuarioId]);

    const handleLogout = () => {
        // Elimina la sesión y redirige a la pantalla de login
        localStorage.removeItem('usuario');
        navigate('/login'); // o la ruta que uses para LoginPage.jsx
    };

    const handleCrearLiga = async (e) => {
        e.preventDefault();
        try {
            await axios.post(
                `http://localhost:8080/api/ligas?usuarioId=${usuarioId}`,
                nuevaLiga
            );
            setMostrarModal(false);
            setNuevaLiga({ nombre: '', privada: false, maxUsuarios: 10, codigoAcceso: '' });
            window.location.reload();
        } catch (error) {
            console.error('Error creando liga:', error);
            const errData = error.response?.data;
            let msg;
            if (typeof errData === 'string') {
                msg = errData;
            } else if (typeof errData === 'object') {
                msg = errData.message || errData.error || JSON.stringify(errData);
            } else {
                msg = 'Error al crear la liga.';
            }
            alert(msg);
        }
    };

    const handleUnirsePublica = async (ligaId) => {
        try {
            await axios.post(
                `http://localhost:8080/api/ligas/${ligaId}/unirse?usuarioId=${usuarioId}`
            );
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert(error.response?.data || 'Error al unirse a la liga.');
        }
    };

    const handleUnirsePrivada = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                nombreLiga: datosUnirsePrivada.nombreLiga,
                claveAcceso: datosUnirsePrivada.claveAcceso,
                usuarioId
            };
            await axios.post(
                'http://localhost:8080/api/ligas/unirsePrivada',
                payload
            );
            setMostrarUnirsePrivada(false);
            setDatosUnirsePrivada({ nombreLiga: '', claveAcceso: '' });
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert(error.response?.data || 'Error al unirse a la liga privada.');
        }
    };

    const irADetalleLiga = (id) => navigate(`/ligas/${id}`);

    const renderLiga = (liga, tipo) => {
        const unido = liga.usuariosIds?.includes(usuarioId);
        const llena = liga.usuariosIds?.length >= liga.maxUsuarios;
        return (
            <li key={liga.id} className="liga-item">
                <span
                    className="liga-item-clickable"
                    onClick={() => irADetalleLiga(liga.id)}
                >
                    {liga.nombre}
                </span>
                {tipo === 'publica' && (
                    unido ? (
                        <button className="btn-unido">Unido</button>
                    ) : llena ? (
                        <button className="btn-llena">Completa</button>
                    ) : (
                        <button className="btn-unirse" onClick={() => handleUnirsePublica(liga.id)}>
                            Unirse
                        </button>
                    )
                )}
            </li>
        );
    };

    return (
        <div className="hub-container">
            {/* HEADER con botones Perfil / Logout */}
            <div className="header-buttons">
                <button className="crear-liga-btn" onClick={() => setMostrarModal(true)}>
                    Crear Liga
                </button>
                <button className="crear-liga-btn" onClick={() => alert('Perfil (no implementado aún)')}>
                    Perfil
                </button>
                <button className="crear-liga-btn" onClick={handleLogout}>
                    Logout
                </button>
            </div>

            <h1 className="hub-title">Hub de Ligas</h1>


            <div className="listas-container">
                <div className="panel-ligas">
                    <h2>Ligas Públicas</h2>
                    <input className="buscar-liga"
                        type="text"
                        placeholder="Buscar públicas"
                        value={filtroPublicas}
                        onChange={e => setFiltroPublicas(e.target.value)}
                    />
                    <ul>
                        {ligasPublicas
                            .filter(l => l.nombre.toLowerCase().includes(filtroPublicas.toLowerCase()))
                            .map(l => renderLiga(l, 'publica'))}
                    </ul>
                </div>

                <div className="panel-ligas">
                    <h2>Ligas Privadas</h2>
                    <input className="buscar-liga"
                        type="text"
                        placeholder="Buscar privadas"
                        value={filtroPrivadas}
                        onChange={e => setFiltroPrivadas(e.target.value)}
                    />
                    <ul>
                        {ligasPrivadas
                            .filter(l => l.nombre.toLowerCase().includes(filtroPrivadas.toLowerCase()))
                            .map(l => renderLiga(l, 'privada'))}
                    </ul>
                    <button className="unirse-btn" onClick={() => setMostrarUnirsePrivada(true)}>
                        Unirse a Liga Privada
                    </button>
                </div>
            </div>

            {mostrarModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2 className="hub-title">Crear Nueva Liga</h2>
                        <form onSubmit={handleCrearLiga}>
                            <input
                                type="text"
                                placeholder="Nombre de la liga"
                                value={nuevaLiga.nombre}
                                onChange={e => setNuevaLiga({ ...nuevaLiga, nombre: e.target.value })}
                                required
                            />
                            <input
                                type="number"
                                placeholder="Máximo de usuarios"
                                value={nuevaLiga.maxUsuarios}
                                onChange={e => setNuevaLiga({ ...nuevaLiga, maxUsuarios: parseInt(e.target.value) })}
                                min={1}
                                required
                            />
                            <label>
                                Privada:
                                <input
                                    type="checkbox"
                                    checked={nuevaLiga.privada}
                                    onChange={e => setNuevaLiga({ ...nuevaLiga, privada: e.target.checked })}
                                />
                            </label>
                            {nuevaLiga.privada && (
                                <input
                                    type="text"
                                    placeholder="Código de acceso"
                                    value={nuevaLiga.codigoAcceso}
                                    onChange={e => setNuevaLiga({ ...nuevaLiga, codigoAcceso: e.target.value })}
                                    required
                                />
                            )}
                            <div className="modal-buttons">
                                <button type="submit" className="crear-liga-btn">Crear</button>
                                <button type="button" className="cancelar-btn" onClick={() => setMostrarModal(false)}>Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}

            {mostrarUnirsePrivada && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2 className="hub-title">Unirse a Liga Privada</h2>
                        <form onSubmit={handleUnirsePrivada}>
                            <input
                                type="text"
                                placeholder="Nombre de la liga"
                                value={datosUnirsePrivada.nombreLiga}
                                onChange={e => setDatosUnirsePrivada({ ...datosUnirsePrivada, nombreLiga: e.target.value })}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Código de acceso"
                                value={datosUnirsePrivada.claveAcceso}
                                onChange={e => setDatosUnirsePrivada({ ...datosUnirsePrivada, claveAcceso: e.target.value })}
                                required
                            />
                            <div className="modal-buttons">
                                <button type="submit" className="btn-unirse">Unirse</button>
                                <button type="button" className="cancelar-btn" onClick={() => setMostrarUnirsePrivada(false)}>Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default HubLigas;
