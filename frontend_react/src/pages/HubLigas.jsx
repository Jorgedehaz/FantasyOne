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

        axios.get('http://localhost:8080/api/ligas/privadas?esPrivada=false')
            .then(res => setLigasPublicas(res.data))
            .catch(err => console.error(err));

        axios.get(`http://localhost:8080/api/ligas/privadas/${usuarioId}`)
            .then(res => setLigasPrivadas(res.data))
            .catch(err => console.error(err));
    }, [usuarioId]);

    const handleCrearLiga = async (e) => {
        e.preventDefault();
        try {
            await axios.post(`http://localhost:8080/api/ligas?usuarioId=${usuarioId}`, nuevaLiga);
            setMostrarModal(false);
            setNuevaLiga({ nombre: '', privada: false, maxUsuarios: 10, codigoAcceso: '' });
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert('Error al crear la liga.');
        }
    };

    const handleUnirsePublica = async (ligaId) => {
        try {
            await axios.post(`http://localhost:8080/api/ligas/${ligaId}/unirse?usuarioId=${usuarioId}`);
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
                usuarioId: usuarioId
            };
            await axios.post('http://localhost:8080/api/ligas/unirsePrivada', payload);
            setMostrarUnirsePrivada(false);
            setDatosUnirsePrivada({ nombreLiga: '', claveAcceso: '' });
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert(error.response?.data || 'Error al unirse a la liga privada.');
        }
    };

    const irADetalleLiga = (id) => {
        navigate(`/ligas/${id}`);
    };

    const renderLiga = (liga, tipo) => {
        const unido = liga.usuariosIds?.includes(usuarioId); // usuarioId del localStorage
        const llena = liga.usuariosIds?.length >= liga.maxUsuarios;

        let botonTexto = 'Unirse';
        let botonClase = 'btn-unirse';

        if (unido) {
            botonTexto = 'Unido';
            botonClase = 'btn-unido';
        } else if (llena) {
            botonTexto = 'Llena';
            botonClase = 'btn-llena';
        }

        return (
            <div key={liga.id} className="liga-item">
                <div className="liga-item-clickable" onClick={() => irADetalleLiga(liga.id)}>
                    {liga.nombre}
                </div>
                {tipo === "publica" && (
                    <button
                        className={botonClase}
                        disabled={unido || llena}
                        onClick={() => handleUnirsePublica(liga.id)}
                    >
                        {botonTexto}
                    </button>
                )}
            </div>
        );
    };

    return (
        <div className="hub-container">
            <div className="listas-container">
                {/* Ligas Públicas */}
                <div className="panel-ligas">
                    <div className="header-panel">
                        <h2 className="text-2xl font-bold mb-4">Ligas Públicas</h2>
                        <button className="crear-liga-btn" onClick={() => setMostrarModal(true)}>Crear Liga</button>
                    </div>
                    <input
                        type="text"
                        placeholder="Buscar públicas"
                        value={filtroPublicas}
                        onChange={(e) => setFiltroPublicas(e.target.value)}
                    />
                    {ligasPublicas
                        .filter(l => l.nombre.toLowerCase().includes(filtroPublicas.toLowerCase()))
                        .map(l => renderLiga(l, "publica"))
                    }
                </div>

                {/* Ligas Privadas */}
                <div className="panel-ligas">
                    <div className="header-panel">
                        <h2 className="text-2xl font-bold mb-4">Tus Ligas Privadas</h2>
                        <div>
                            <button className="crear-liga-btn" onClick={() => setMostrarModal(true)}>Crear Liga</button>
                            <button className="crear-liga-btn" onClick={() => setMostrarUnirsePrivada(true)}>Unirse a Privada</button>
                        </div>
                    </div>
                    <input
                        type="text"
                        placeholder="Buscar privadas"
                        value={filtroPrivadas}
                        onChange={(e) => setFiltroPrivadas(e.target.value)}
                    />
                    {ligasPrivadas
                        .filter(l => l.nombre.toLowerCase().includes(filtroPrivadas.toLowerCase()))
                        .map(l => renderLiga(l, "privada"))
                    }
                </div>
            </div>

            {/* Modal */}
            {mostrarModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Crear Nueva Liga</h2>
                        <form onSubmit={handleCrearLiga}>
                            <input type="text" placeholder="Nombre" required value={nuevaLiga.nombre} onChange={(e) => setNuevaLiga({ ...nuevaLiga, nombre: e.target.value })} />
                            <input type="number" placeholder="Máx. Usuarios" required value={nuevaLiga.maxUsuarios} onChange={(e) => setNuevaLiga({ ...nuevaLiga, maxUsuarios: parseInt(e.target.value) })} />
                            <label>
                                Privada:
                                <input type="checkbox" checked={nuevaLiga.privada} onChange={(e) => setNuevaLiga({ ...nuevaLiga, privada: e.target.checked })} />
                            </label>
                            {nuevaLiga.privada && (
                                <input type="text" placeholder="Código Acceso" value={nuevaLiga.codigoAcceso} onChange={(e) => setNuevaLiga({ ...nuevaLiga, codigoAcceso: e.target.value })} />
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
                        <h2>Unirse a Liga Privada</h2>
                        <form onSubmit={handleUnirsePrivada}>
                            <input
                                type="text"
                                placeholder="Nombre de la liga"
                                required
                                value={datosUnirsePrivada.nombreLiga}
                                onChange={(e) => setDatosUnirsePrivada({ ...datosUnirsePrivada, nombreLiga: e.target.value })}
                            />
                            <input
                                type="text"
                                placeholder="Código de acceso"
                                required
                                value={datosUnirsePrivada.claveAcceso}
                                onChange={(e) => setDatosUnirsePrivada({ ...datosUnirsePrivada, claveAcceso: e.target.value })}
                            />
                            <div className="modal-buttons">
                                <button type="submit" className="crear-liga-btn">Unirse</button>
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
