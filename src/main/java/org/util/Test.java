package org.util;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class Test {
    public static void main(String[] args) throws IOException {
        String path = "E:\\aaa";
        int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED
                | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
        boolean watchSubtree = true;
        int watchID = JNotify.addWatch(path, mask, watchSubtree,
                new JNotifyListener() {
                    public void fileRenamed(final int wd,
                                            final String rootPath, final String oldName,
                                            final String newName) {
                        System.out.println("JNotifyTest.fileRenamed() : wd #"
                                + wd + " root = " + rootPath + ", " + oldName
                                + " -> " + newName);
                    }

                    public void fileModified(final int wd,
                                             final String rootPath, final String name) {
//                        System.out.println("JNotifyTest.fileModified() : wd #"
//                                + wd + " root = " + rootPath + ", " + name);
                    }

                    public void fileDeleted(final int wd,
                                            final String rootPath, final String name) {
                        System.out.println("JNotifyTest.fileDeleted() : wd #"
                                + wd + " root = " + rootPath + ", " + name);
                    }

                    public void fileCreated(final int wd,
                                            final String rootPath, final String name) {
                        System.out.println("JNotifyTest.fileCreated() : wd #"
                                + wd + " root = " + rootPath + ", " + name);
                        String[] s = {rootPath + "\\" + name, rootPath + "\\" + name};
                        try {
                            URL[] urls = new URL[]{new URL("file:///" + rootPath + "\\" + name)};
                            URLClassLoader myClassLoader = new URLClassLoader(urls);
                            myClassLoader.loadClass("org.homework.RPC." + name.split("\\.")[0]).newInstance().equals(new Object());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        try {
//                            CompileClassLoader.start(s);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                });
        System.in.read();
        boolean res = JNotify.removeWatch(watchID);
        if (!res) {
            System.out.println("error");
        }
        System.out.println("ok");
    }
}
