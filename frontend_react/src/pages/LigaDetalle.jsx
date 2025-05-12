import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from 'axios';
import './LigaDetalle.css';

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);
    const [mercado, setMercado] = useState(null);
    const [pilotos, setPilotos] = useState([]);

    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) {
            window.location.href = "/login";
            return;
        }

        axios.get(`/api/ligas/${id}`)
            .then(response => setLiga(response.data))
            .catch(error => {
                console.error('Error cargando liga:', error);
            });
    }, [id]);

    useEffect(() => {
        axios.get(`/api/mercado/liga/${id}`)
            .then(res => {
                setMercado(res.data);
                return axios.get(`/api/piloto-mercado/mercado/${res.data.id}`);
            })
            .then(res => setPilotos(res.data))
            .catch(err => console.error("Error cargando mercado o pilotos:", err));
    }, [id]);

    if (!liga) return <p>Cargando liga...</p>;

    return (
        <div style={{ padding: "20px" }}>
            <h2>Detalles de la Liga</h2>
            <p><strong>Nombre:</strong> {liga.nombre}</p>
            <p><strong>Privada:</strong> {liga.privada ? 'Sí' : 'No'}</p>
            <p><strong>Máximo de jugadores:</strong> {liga.maxUsuarios}</p>
            <p><strong>Jugadores actuales:</strong> {liga.usuariosId.length}</p>

            <h3>Mercado actual</h3>
            <p><strong>Fecha:</strong> {mercado?.fecha}</p>

            {pilotos.length === 0 ? (
                <p>No hay pilotos disponibles en el mercado actualmente.</p>
            ) : (
                <ul>
                    {pilotos.map(piloto => (
                        <li key={piloto.id}>
                            {piloto.nombrePiloto} - Precio: {piloto.precio} € {piloto.fichado ? "(Fichado)" : ""}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default LigaDetalle;
