import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './LigaDetalle.css';

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);

    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) {
            window.location.href = "/login";
            return;
        }

        const fetchLiga = async () => {
            try {
                const res = await axios.get(`http://localhost:8080/api/ligas/${id}`);
                setLiga(res.data);
            } catch (err) {
                console.error("Error al obtener liga:", err);
            }
        };
        fetchLiga();
    }, [id]);

    if (!liga) return <div className="liga-detalle-loading">Cargando liga...</div>;

    return (
        <div className="liga-detalle-container">
            <h1 className="liga-detalle-titulo">{liga.nombre}</h1>
            <p>Privada: {liga.privada ? 'Sí' : 'No'}</p>
            <p>Máximo de jugadores: {liga.maxUsuarios}</p>
            <p>Jugadores actuales: {liga.usuariosIds?.length}</p>
            {/* Aquí después se añadirá equipo, mercado, clasificaciones, etc */}
        </div>
    );
};

export default LigaDetalle;
