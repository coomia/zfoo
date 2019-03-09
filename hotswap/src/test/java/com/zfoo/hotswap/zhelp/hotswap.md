1. 只能直接运行run，不能以调试debug运行，因为如果以debug模式运行的话，idea会自动热更新java源代码；
2. 将hotswap用mavenpackage打包成jar，放在hotscript中；
3. recompile需要更新的java源文件，生成class字节码；
4. 将需要更新的class文件放在hotscript文件夹中更新；
5. 执行更新命令，更新完成之后自动删除文件；