import { defineConfig } from "vite";
import { resolve, dirname } from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export default defineConfig({
  // Root directory where source files are located
  root: resolve(__dirname, "SampleWeb/src/main/webui"),

  // Build configuration
  build: {
    // Output directory matching your tsconfig.json outDir
    outDir: resolve(__dirname, "SampleWeb/target/SampleWeb/ui"),

    // Empty output directory before building
    emptyOutDir: true,

    // Generate source maps for debugging
    sourcemap: true,

    // Rollup options
    rollupOptions: {
      input: {
        // Entry point for your application
        main: resolve(__dirname, "SampleWeb/src/main/webui/main.ts"),
      },
      output: {
        // Output format
        format: "es",

        // Entry file naming
        entryFileNames: "[name].js",

        // Chunk file naming (for code splitting)
        chunkFileNames: "chunks/[name]-[hash].js",

        // Asset file naming (CSS, images, etc.)
        assetFileNames: "assets/[name]-[hash].[ext]",
      },
    },

    // Minification settings
    minify: "esbuild",

    // Target browsers
    target: "es2020",
  },

  // Development server configuration (optional, for local dev)
  server: {
    port: 3000,
    strictPort: false,
  },

  // Preview server configuration (optional)
  preview: {
    port: 3000,
  },

  // Resolve configuration
  resolve: {
    alias: {
      "@": resolve(__dirname, "SampleWeb/src/main/webui"),
      "@test-utils": resolve(__dirname, "SampleWeb/src/main/webui/test-utils"),
    },
  },
});
