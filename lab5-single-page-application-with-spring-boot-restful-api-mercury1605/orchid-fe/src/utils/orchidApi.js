import axios from 'axios';

const orchidApi = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getAllOrchids = () => orchidApi.get('/orchids/');
export const getOrchidById = (id) => orchidApi.get(`/orchids/${id}`);
export const createOrchid = (data) => orchidApi.post('/orchids/', data);
export const updateOrchid = (id, data) => orchidApi.put(`/orchids/${id}`, data);
export const deleteOrchid = (id) => orchidApi.delete(`/orchids/${id}`);
