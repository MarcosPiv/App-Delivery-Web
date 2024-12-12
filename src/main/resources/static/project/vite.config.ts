import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  optimizeDeps: {
    exclude: ['lucide-react'],
  },
  server: {
    port: 3000,
    watch: {
      ignored: ['/proc/**'], // Ignorar directorios problemáticos
    },
    host: true, // Habilitar el acceso externo
    strictPort: true,
  },
});
