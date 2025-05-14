import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import './LigaDetalle.css';

const LigaDetalle = () => {
    const { id } = useParams();
    const [liga, setLiga] = useState(null);
    const [mercado, setMercado] = useState(null);

    // Comprueba sesión al montar
    useEffect(() => {
        const usuario = JSON.parse(localStorage.getItem("usuario"));
        if (!usuario) {
            window.location.href = "/login";
            return;
        }

        // Carga datos de la liga
        axios.get(`/api/ligas/${id}`)
            .then(response => setLiga(response.data))
            .catch(error => console.error('Error cargando liga:', error));

        // Carga mercado actual con pilotos incluidos
        axios.get(`/api/mercados/liga/${id}`)
            .then(res => setMercado(res.data))
            .catch(err => console.error('Error cargando mercado:', err));
    }, [id]);

    if (!liga) return <p>Cargando liga...</p>;

    return (
        <div className="liga-detalle-container">
            <h2>Detalles de la Liga</h2>
            <p><strong>Nombre:</strong> {liga.nombre}</p>
            <p><strong>Privada:</strong> {liga.privada ? 'Sí' : 'No'}</p>
            <p><strong>Máximo de jugadores:</strong> {liga.maxUsuarios}</p>
            <p><strong>Jugadores actuales:</strong> {liga.usuariosId.length}</p>

            <h3>Mercado actual</h3>
            <p><strong>Fecha:</strong> {mercado?.fecha || '—'}</p>

            {mercado?.pilotos && mercado.pilotos.length > 0 ? (
                <ul className="pilotos-lista">
                    {mercado.pilotos.map(piloto => (
                        <li key={piloto.id} className="piloto-item">
                            <img
                                src={piloto.imagenUrl}
                                alt={piloto.nombreCompleto}
                                className="piloto-imagen"
                            />
                            <div className="piloto-datos">
                                <span className="piloto-nombre">{piloto.nombreCompleto}</span>
                                <span className="piloto-precio">{piloto.precio} €</span>
                                {piloto.fichado && <span className="piloto-fichado">(Fichado)</span>}
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No hay pilotos disponibles en el mercado actualmente.</p>
            )}
        </div>
    );
};

export default LigaDetalle;
