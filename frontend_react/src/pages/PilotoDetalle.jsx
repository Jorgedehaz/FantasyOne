import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './PilotoDetalle.css';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    BarElement,
    Title,
    Tooltip,
    Legend
} from 'chart.js';
import { Line, Bar } from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    BarElement,
    Title,
    Tooltip,
    Legend
);

const PilotoDetalle = ({ piloto, show, onClose }) => {
    const [history, setHistory] = useState(null);

    useEffect(() => {
        if (piloto?.pilotoExternalId && show) {
            axios
                .get(`/api/resultados/piloto/${piloto.pilotoExternalId}`)
                .then(res => setHistory(res.data))
                .catch(() => setHistory([]));
        }
    }, [piloto, show]);

    if (!show || !piloto) return null;
    if (history === null) return (
        <div className="modal-overlay">
            <div className="modal-content">
                <p>Cargando resultados…</p>
            </div>
        </div>
    );

    // Estadísticas
    const poleCount    = history.filter(r => r.polePosition).length;
    const fastLapCount = history.filter(r => r.vueltaRapida).length;
    const penaltyCount = history.filter(r => r.penalizado).length;

    // Datos línea
    const lineData = {
        labels: history.map(r => new Date(r.momento).toLocaleDateString()),
        datasets: [
            {
                label: 'Puntos Fantasy',
                data: history.map(r => r.puntosFantasy),
                borderColor: 'rgba(215, 0, 0, 0.7)',
                backgroundColor: '#D70000',
                fill: false,
                tension: 0.2
            }
        ]
    };

    // Datos barras
    const barData = {
        labels: ['Poles', 'Vueltas Rápidas', 'Penalizaciones'],
        datasets: [
            {
                label: 'Estadísticas',
                data: [poleCount, fastLapCount, penaltyCount],
                backgroundColor: 'rgba(215, 0, 0, 0.6)'
            }
        ]
    };

    // Opciones comunes
    const chartOptions = {
        maintainAspectRatio: false,
        plugins: {
            legend: {
                labels: { color: '#FFFFFF' }
            },
            tooltip: {
                titleColor: '#FFFFFF',
                bodyColor: '#FFFFFF'
            }
        },
        scales: {
            x: {
                grid: { color: 'rgba(255, 255, 255, 0.2)' },
                ticks: { color: '#FFFFFF' }
            },
            y: {
                beginAtZero: true,
                min: 0,
                grid: { color: 'rgba(255, 255, 255, 0.2)' },
                ticks: { color: '#FFFFFF' }
            }
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="close-button" onClick={onClose}>×</button>
                <div className="piloto-header">
                    <img
                        src={piloto.imagenUrl}
                        alt={piloto.nombreCompleto}
                        className="piloto-img-large"
                    />
                    <div className="piloto-info">
                        <h2>{piloto.nombreCompleto}</h2>
                        <p><strong>Precio:</strong> {piloto.precio} €</p>
                        <p><strong>País:</strong> {piloto.pais}</p>
                        <p><strong>Número:</strong> {piloto.numero}</p>
                        <p><strong>Equipo:</strong> {piloto.equipo}</p>
                        <p><strong>Puntos Fantasy:</strong> {piloto.puntosFantasy} pts</p>
                    </div>
                </div>
                <div className="charts-container">
                    <div className="chart-wrapper">
                        <Line data={lineData} options={chartOptions} />
                    </div>
                    <div className="chart-wrapper">
                        <Bar data={barData} options={chartOptions} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PilotoDetalle;
