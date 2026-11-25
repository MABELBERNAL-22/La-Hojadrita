import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Config normal de Vite, nada raro. El proxy es para que el front
// (puerto 5173) pueda hablar con el backend de Spring Boot (puerto 8080)
// sin que el navegador se queje de CORS mientras se desarrolla.
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});
