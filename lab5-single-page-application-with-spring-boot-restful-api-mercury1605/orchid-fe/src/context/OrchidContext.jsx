import { createContext, useCallback, useContext, useReducer } from 'react';
import { ACTIONS, initialState, orchidReducer } from '../reducers/orchidReducer';
import {
  getAllOrchids,
  getOrchidById,
  createOrchid,
  updateOrchid,
  deleteOrchid,
} from '../utils/orchidApi';

const OrchidContext = createContext(null);

export function OrchidProvider({ children }) {
  const [state, dispatch] = useReducer(orchidReducer, initialState);

  const fetchOrchids = useCallback(async () => {
    dispatch({ type: ACTIONS.FETCH_START });
    try {
      const res = await getAllOrchids();
      dispatch({ type: ACTIONS.FETCH_SUCCESS, payload: res.data });
    } catch (err) {
      dispatch({ type: ACTIONS.FETCH_ERROR, payload: err.message });
    }
  }, []);

  const addOrchid = useCallback(async (data) => {
    const res = await createOrchid(data);
    dispatch({ type: ACTIONS.ADD, payload: res.data });
  }, []);

  const editOrchid = useCallback(async (id, data) => {
    const res = await updateOrchid(id, data);
    dispatch({ type: ACTIONS.UPDATE, payload: res.data });
  }, []);

  const removeOrchid = useCallback(async (id) => {
    await deleteOrchid(id);
    dispatch({ type: ACTIONS.DELETE, payload: id });
  }, []);

  const fetchOrchidById = useCallback(async (id) => {
    const res = await getOrchidById(id);
    return res.data;
  }, []);

  return (
    <OrchidContext.Provider
      value={{ ...state, fetchOrchids, fetchOrchidById, addOrchid, editOrchid, removeOrchid }}
    >
      {children}
    </OrchidContext.Provider>
  );
}

export function useOrchid() {
  const context = useContext(OrchidContext);
  if (!context) {
    throw new Error('useOrchid must be used within an OrchidProvider');
  }
  return context;
}
