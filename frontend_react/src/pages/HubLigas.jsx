import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './HubLigas.css';

const HubLigas = () => {
    const [ligasPublicas, setLigasPublicas] = useState([]);
    const [ligasPrivadas, setLigasPrivadas] = useState([]);
    const [filtroPublicas, setFiltroPublicas] = useState('');
    const [filtroPrivadas, setFiltroPrivadas] = useState('');
    const [mostrarModal, setMostrarModal] = useState(false);
    const [nuevaLiga, setNuevaLiga] = useState({ nombre: '', privada: false, maxUsuarios: 10, codigoAcceso: '' });

    const usuario = JSON.parse(localStorage.getItem('usuario'));
    const usuarioId = usuario?.id;

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
            await axios.post('http://localhost:8080/api/ligas', nuevaLiga);
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
            await axios.post(`http://localhost:8080/api/ligas/${ligaId}/unirse?usuarioId=${usuario.id}`);
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert('Error al unirse a la liga.');
        }
    };

    // Función para saber si el usuario ya está unido
    const estaUnidoALiga = (ligaId) => {
        return ligasPrivadas.some(liga => liga.id === ligaId);
    };


    const ligasPublicasFiltradas = ligasPublicas.filter(liga => liga.nombre.toLowerCase().includes(filtroPublicas.toLowerCase()));
    const ligasPrivadasFiltradas = ligasPrivadas.filter(liga => liga.nombre.toLowerCase().includes(filtroPrivadas.toLowerCase()));

    return (
        <div className="hub-container">
            <h1 className="hub-title">Bienvenido al Hub de Ligas</h1>
            <button className="crear-liga-btn" onClick={() => setMostrarModal(true)}>Crear Nueva Liga</button>

            <div className="listas-container">
                <div className="panel-ligas">
                    <h2>Ligas Públicas</h2>
                    <input type="text" placeholder="Buscar públicas..." value={filtroPublicas} onChange={(e) => setFiltroPublicas(e.target.value)} />
                    {ligasPublicasFiltradas.map(liga => (
                        <div key={liga.id} className="liga-item">
                            <span>{liga.nombre}</span>
                            {liga.maxUsuarios > 0 ? (
                                <button
                                    onClick={() => handleUnirsePublica(liga.id)}
                                    disabled={estaUnidoALiga(liga.id) || liga.usuarios?.length >= liga.maxUsuarios}
                                    className={`boton-unirse ${estaUnidoALiga(liga.id) || liga.usuarios?.length >= liga.maxUsuarios ? 'boton-desactivado' : ''}`}
                                >
                                    {estaUnidoALiga(liga.id) ? 'Ya unido' : (liga.usuarios?.length >= liga.maxUsuarios ? 'Llena' : 'Unirse')}
                                </button>
                            ) : (
                                <button className="completa-btn" disabled>Completa</button>
                            )}
                        </div>
                    ))}
                </div>

                <div className="panel-ligas">
                    <h2>Tus Ligas Privadas</h2>
                    <input type="text" placeholder="Buscar privadas..." value={filtroPrivadas} onChange={(e) => setFiltroPrivadas(e.target.value)} />
                    {ligasPrivadasFiltradas.map(liga => (
                        <div key={liga.id} className="liga-item">
                            <span>{liga.nombre}</span>
                        </div>
                    ))}
                </div>
            </div>

            {mostrarModal && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Crear Nueva Liga</h2>
                        <form onSubmit={handleCrearLiga}>
                            <input type="text" placeholder="Nombre" required value={nuevaLiga.nombre} onChange={(e) => setNuevaLiga({ ...nuevaLiga, nombre: e.target.value })} />
                            <input type="number" placeholder="Máx. Usuarios" required value={nuevaLiga.maxUsuarios} onChange={(e) => setNuevaLiga({ ...nuevaLiga, maxUsuarios: e.target.value })} />
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
        </div>
    );
};

export default HubLigas;
