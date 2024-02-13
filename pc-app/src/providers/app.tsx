import * as React from "react";

import { HashRouter as Router } from "react-router-dom";
import { NextUIProvider } from "@nextui-org/react";
import { Spinner } from "@/components/Elements";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RecoilRoot } from "recoil";
import {
  StompSessionProvider, 
} from "react-stomp-hooks";

// Import FilePond styles
import "filepond/dist/filepond.min.css";

import FilePondPluginImageExifOrientation from "filepond-plugin-image-exif-orientation";
import FilePondPluginImagePreview from "filepond-plugin-image-preview";
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css";
import { registerPlugin } from "react-filepond";
import { SOCKET_URL } from "@/config";
import Cookies from "js-cookie";

// Register the plugins
registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview);

const queryClient = new QueryClient();
type AppProviderProps = {
  children: React.ReactNode;
};

export const AppProvider = ({ children }: AppProviderProps) => {

  return (
    <RecoilRoot>
      <StompSessionProvider
        url={`ws://${SOCKET_URL}/stomp/chat`}
          connectHeaders={{
          Authorization: Cookies.get("accessToken") ?? "", 
        }}
      >
      <QueryClientProvider client={queryClient}>
        <React.Suspense
          fallback={
            <div className="flex items-center justify-center w-screen h-screen">
              <Spinner size="xl" />
            </div>
          }
        >
          <NextUIProvider>
            <Router>{children}</Router>
          </NextUIProvider>
        </React.Suspense>
        </QueryClientProvider>
        </StompSessionProvider>
    </RecoilRoot>
  );
};