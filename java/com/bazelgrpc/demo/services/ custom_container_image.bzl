def java_container_image(name, pkg,srcs, deps):
    for src in srcs:
        src_name = src[:-5]
        native.java_test(name=src_name , test_class=pkg+'.'+src_name , srcs=srcs, deps=deps, size="small")


